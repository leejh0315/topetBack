package topetBack.topetBack.member.application;

import java.util.Map;

public interface SocialLoginService {
	Map<String, Object>  kakaoLogin(String code)throws Exception;
}
