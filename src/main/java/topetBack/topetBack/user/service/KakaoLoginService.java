package topetBack.topetBack.user.application;

import java.util.Map;

public interface KakaoLoginService {
	Map<String, Object>  kakaoLogin(String code)throws Exception;
}
