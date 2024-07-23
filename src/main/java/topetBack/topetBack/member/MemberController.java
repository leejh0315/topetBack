package topetBack.topetBack.member;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.config.SessionManager;
import topetBack.topetBack.member.application.MemberService;
import topetBack.topetBack.member.application.SocialLoginService;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.SessionMember;

@RestController
@Slf4j
@RequiredArgsConstructor

public class MemberController {

	@Value("${toPet.back.address}")
	private String backAddress;

	@Value("${toPet.front.address}")
	private String fronAddress;

	@Value("${toPet.kakao.clientId}")
	private String clientId;

	private final SocialLoginService kakaoLoginService;

	private final MemberService memberService;

	private final SessionManager sessionManager;

	@GetMapping("/kakaoLogin")
	public String getKakaoLogin() {

		System.out.println("get KakaoLogin");
		String redirectUri = backAddress + "api/kakaoLogin/OAuth";
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

		Map<String, Object> response = kakaoLoginService.kakaoLogin(code);
		RedirectView redirectView = new RedirectView();

		if (response != null) {
			Member member = new Member(0L, response.get("kid").toString(), (String) response.get("email"),
					(String) response.get("nickname"), null);
			String sId = response.get("kid").toString();
			Optional<Member> dbMember = memberService.findBySocialId(sId);
			if (dbMember.isPresent()) {
				System.out.println("주입할 필요 없어서 안했음");
			} else {
				memberService.memberJoin(member);
				System.out.println("주입완료");
			}
			Member newMember = memberService.findBySocialId(response.get("kid").toString()).get();
			String sessionId = sessionManager.create(newMember, resp);
			redirectView.setUrl(fronAddress + "api/home");
		} else {
			redirectView.setUrl(fronAddress + "api");
		}
		return redirectView;
	}

	@Transactional
	@GetMapping("/home")
	public String getHome(HttpServletRequest req) throws JsonMappingException, JsonProcessingException {
		SessionMember member = sessionManager.getSessionObject(req);
		System.out.println(member);
		return member.toString();
	}
}
