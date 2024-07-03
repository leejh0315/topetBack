package topetBack.topetBack.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import topetBack.topetBack.service.KakaoLoginService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {
	
	@Value("${toPet.back.address}")
	private String backAddress;
	
	private final KakaoLoginService kakaoLoginService;
		
	@GetMapping("/api/kakaoLogin")
    @ResponseBody
    public String getKakaoLogin() {
        log.info("get KakaoLogin");
        return "get kakaoLogin";
    }
	
	@GetMapping("/api/kakaoLogin/OAuth")
	public @ResponseBody  String getOAuth(@RequestParam("code") String code) throws Exception {// 
		log.info("get kakaoLogin/OAuth");
		System.out.println(backAddress);
		
		String response = kakaoLoginService.kakaoLogin(code);
		
		return response.toString();
	}
	
	
}
