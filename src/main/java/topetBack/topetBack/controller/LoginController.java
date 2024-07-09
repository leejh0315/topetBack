package topetBack.topetBack.controller;


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
import topetBack.topetBack.domain.Member;
import topetBack.topetBack.service.KakaoLoginService;
import topetBack.topetBack.service.MemberService;
import topetBack.topetBack.session.SessionVar;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {
	
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
	
	@GetMapping("/api/kakaoLogin/OAuth")
	public @ResponseBody  String getOAuth(@RequestParam("code") String code, HttpServletRequest req) throws Exception {// 
		log.info("get kakaoLogin/OAuth");
		
		
		Map<String, Object> response = kakaoLoginService.kakaoLogin(code);
		
		System.out.println("kid = " + response.get("kid"));
		
		
		if(response != null) {
			Member member = new Member( (Long) response.get("kid"), 
										(String) response.get("email"), 
										(String) response.get("nickname")
										);
			
			
			Optional<Member> dbMember= memberService.selectByKakaoId((Long) response.get("kid"));
			
			if(dbMember.isEmpty()) {
				memberService.memberJoin(member);
				System.out.println("주입완료");
			}else {
				HttpSession session = req.getSession(true);//세션에 정보가 없을 때, null을 반환하는 것이 아닌 새로운 객체를 생성하여 반환
			    session.setAttribute(SessionVar.LOGIN_MEMBER, dbMember);
			}
			
			
		}
		
	
		
//		
//	    
//	      
//	    member.setActiveUUID(session.getId());
	    
		
		return response.toString();
	}
	
	
}
