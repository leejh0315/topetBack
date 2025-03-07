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
		if (response.isEmpty()) {
			redirectView.setUrl(frontAddress + "/login");
			return redirectView;
		}
		
		String defaultProfileSrc = "https://i.pinimg.com/564x/57/70/f0/5770f01a32c3c53e90ecda61483ccb08.jpg";
		Member member = new Member(0L, response.get("kid").toString(), response.get("email").toString(),
				response.get("nickname").toString(), defaultProfileSrc.toString(), new FileGroupEntity());
		
		String sId = response.get("kid").toString();
		Optional<Member> dbMember = memberService.findBySocialId(sId);
		
		
		if (dbMember.isEmpty()) {
			
			memberService.memberJoin(member);
			System.out.println("주입완료");
			Member newMember = memberService.findBySocialId(response.get("kid").toString()).get();
			String sessionId = sessionManager.create(newMember.getId(), resp);
			redirectView.setUrl(frontAddress + "/userregister");
			return redirectView;
		} else {
			System.out.println("주입할 필요 없어서 안했음");
			Member newMember = memberService.findBySocialId(response.get("kid").toString()).get();
//			List<MemberPet> memberPet = memberService.findByMember(newMember);
//			List<PetResponseDTO> pets = new ArrayList<PetResponseDTO>();
//			for (int i = 0; i < memberPet.size(); i++) {
//				pets.add(petService.findById(memberPet.get(i).getPet().getId()));
//			}
			String sessionId = sessionManager.create(newMember.getId(), resp);
			redirectView.setUrl(frontAddress + "/login");
			return redirectView;
		}
	}
//	@PostMapping("/userregister")
//	public ResponseEntity<Member> postUserRegister(HttpServletRequest req, HttpServletResponse resp, 
//													@RequestParam(value="profileName", required=false) String profileName,
//													@RequestParam(value="photo", required=false) MultipartFile image
//													) throws IOException{
//		
//		Member member = sessionManager.getSessionObject(req).toMember();
//		Member newMember = memberService.userInfoRegister(member, profileName, image);
////		sessionManager.refreshMember(newMember, resp, req);
//		return ResponseEntity.ok(member);
//	}

	@PatchMapping("/update")
	public ResponseEntity<MemberResponseDTO> updateMember(MemberRequestDTO memberRequestDTO) throws IOException {

		System.out.println("memberRequestDTO : " + memberRequestDTO);

		MemberResponseDTO updatedMember = memberService.updateMember(memberRequestDTO);
		return ResponseEntity.ok(updatedMember);
	}

	@GetMapping("/home")
	public ResponseEntity<MemberResponseDTO> getHome(HttpServletRequest req) throws JsonProcessingException {
		Long memberId = sessionManager.getSessionObject(req);
		System.out.println("home요청");
		MemberResponseDTO memberResponseDTO = memberService.findById(memberId);
		return ResponseEntity.ok(memberResponseDTO);
	}

//	@GetMapping("/getMyPet/{id}")
//	public ResponseEntity<PetResponseDTO> getPet(@PathVariable("id")Long id){
//
//		PetResponseDTO pet = petService.findById(id);
//		System.out.println(ResponseEntity.ok(pet));
//		return ResponseEntity.ok(pet);
//	}

	// 로그아웃
	@PostMapping("/logout")
	public String logout(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("Logout 요청");
		String result = sessionManager.remove(req, resp);
		if (result.equals("success")) {
			return "success";
		} else {
			return "fail";
		}
	}
	
	@PostMapping("/secession/{id}")
	public String secession(@PathVariable("id")Long memberId , HttpServletRequest req, HttpServletResponse resp) {
		
		memberService.deleteById(memberId);
		String result = sessionManager.remove(req, resp);
		if (result.equals("success")) {
			return "success";
		} else {
			return "fail";
		}
		
	}

}