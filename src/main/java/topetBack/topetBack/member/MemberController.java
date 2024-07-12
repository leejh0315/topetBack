package topetBack.topetBack.member;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.config.SessionVar;
import topetBack.topetBack.member.application.KakaoLoginService;
import topetBack.topetBack.member.application.MemberService;
import topetBack.topetBack.member.domain.Member;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {

	@Value("${toPet.back.address}")
	private String backAddress;

	private final KakaoLoginService kakaoLoginService;

	private final MemberService memberService;

	@GetMapping("/kakaoLogin")
	@ResponseBody
	public String getKakaoLogin() {
		log.info("get KakaoLogin");
		return "get kakaoLogin";
	}

	@GetMapping("/kakaoLogin/OAuth")
	public @ResponseBody String getOAuth(@RequestParam("code") String code, HttpServletRequest req) throws Exception {//
		log.info("get kakaoLogin/OAuth");

		Map<String, Object> response = kakaoLoginService.kakaoLogin(code);

		System.out.println("kid = " + response.get("kid"));

		if (response != null) {
			Member member = new Member(0, response.get("kid").toString(), (String) response.get("email"),
					(String) response.get("nickname"));

			Optional<Member> dbMember = memberService.findBySocialId(response.get("kid").toString());

			if (dbMember.isEmpty()) {
				memberService.memberJoin(member);
				System.out.println("주입완료");
				dbMember = memberService.findBySocialId(response.get("kid").toString());
			}
			 HttpSession session = req.getSession(true);
			 session.setAttribute(SessionVar.LOGIN_MEMBER, dbMember.get());
		}
	     
//	    member.setActiveUUID(session.getId());

		return response.toString();
	}
	@GetMapping("/home")
	public String getHome(HttpServletRequest req) {
		System.out.println("getHOme");
		HttpSession session = req.getSession(true);
		Member member = (Member) session.getAttribute(SessionVar.LOGIN_MEMBER);
	    System.out.println(member);
		return member.toString();
	}
}
