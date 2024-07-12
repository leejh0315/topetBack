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

	@GetMapping("/api/kakaoLogin")
	@ResponseBody
	public String getKakaoLogin() {
		log.info("get KakaoLogin");
		return "get kakaoLogin";
	}


}
