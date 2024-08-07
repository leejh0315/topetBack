package topetBack.topetBack.member;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.config.CookieUtils;
import topetBack.topetBack.config.SessionManager;
import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.member.application.MemberService;
import topetBack.topetBack.member.application.SocialLoginService;
import topetBack.topetBack.member.domain.*;
import topetBack.topetBack.pet.application.PetService;
import topetBack.topetBack.pet.domain.PetResponseDTO;
import topetBack.topetBack.schedule.application.ScheduleService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	@Value("${toPet.back.address}")
	private String backAddress;

	@Value("${toPet.front.address}")
	private String frontAddress;

	@Value("${toPet.kakao.clientId}")
	private String clientId;

	private final SocialLoginService kakaoLoginService;
	private final MemberService memberService;
	@Autowired
	private final PetService petService;
	private final SessionManager sessionManager;
	private final ScheduleService scheduleService;
	
	
	@GetMapping("/kakaoLogin")
	public String getKakaoLogin() {
		System.out.println("get KakaoLogin");
		String redirectUri = backAddress + "api/member/kakaoLogin/OAuth";
		StringBuffer url = new StringBuffer();
		url.append("https://kauth.kakao.com/oauth/authorize?");
		url.append("client_id=" + clientId);
		url.append("&redirect_uri=" + redirectUri);
		url.append("&response_type=code");
		return url.toString();
	}

	@GetMapping("/kakaoLogin/OAuth")
	public RedirectView getOAuth(@RequestParam("code") String code, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		RedirectView redirectView = new RedirectView();
		Map<String, Object> response = kakaoLoginService.kakaoLogin(code);
		if (response != null) {
			Member member = new Member(0L, response.get("kid").toString(), (String) response.get("email"), (String) response.get("nickname"), "", new FileGroupEntity());
			String sId = response.get("kid").toString();
			Optional<Member> dbMember = memberService.findBySocialId(sId);
			if (dbMember.isPresent()) {
				System.out.println("주입할 필요 없어서 안했음");
			} else {
				memberService.memberJoin(member);
				System.out.println("주입완료");
				redirectView.setUrl(frontAddress + "/userregister");
			}
			Member newMember = memberService.findBySocialId(response.get("kid").toString()).get();
			List<MemberPet> memberPet = memberService.findByMember(newMember);
			List<PetResponseDTO> pets = new ArrayList<PetResponseDTO>();
			for(int i = 0 ; i < memberPet.size(); i++) {
				pets.add(petService.findById(memberPet.get(i).getPet().getId()));
			}
			String sessionId = sessionManager.create(newMember, pets, resp);
			redirectView.setUrl(frontAddress + "/home");
		} else {
			redirectView.setUrl(frontAddress + "/");
		}
		return redirectView;
	}
	
	@PostMapping("/userregister")
	public ResponseEntity<Member> postUserRegister(HttpServletRequest req, HttpServletResponse resp, 
													@RequestParam(value="profileName", required=false) String profileName,
													@RequestParam(value="photo", required=false) MultipartFile image
													) throws IOException{
		
		Member member = sessionManager.getSessionObject(req).toMember();
		Member newMember = memberService.userInfoRegister(member, profileName, image);
		sessionManager.refreshMember(newMember, resp, req);
		return ResponseEntity.ok(member);
	}

	@PatchMapping("/update")
	public ResponseEntity<SessionMember> updateMember(HttpServletRequest req, MemberRequestDTO memberRequestDTO) throws JsonMappingException, JsonProcessingException {

		Cookie cookie = CookieUtils.findCookieFromRequest(req);
		memberRequestDTO.setCookie(cookie);

		SessionMember updatedSessionMember = memberService.updateMember(memberRequestDTO);

		return ResponseEntity.ok(updatedSessionMember);
	}

	@GetMapping("/home")
	public SessionMember getHome(HttpServletRequest req) throws JsonMappingException, JsonProcessingException {
		SessionMember member = sessionManager.getSessionObject(req);
		return member;
	}
	
	//로그아웃
	@PostMapping("/logout")
	public String logout(HttpServletRequest req) {
		String result = sessionManager.remove(req);
		if(result.equals("success")) {
			return "success";
		}else{
			return "fail";
		}
	}
	

}