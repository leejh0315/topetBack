package topetBack.topetBack.user.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.user.domain.OAuthToken;

@Service
@Slf4j
public class KakaoLoginServiceImpl implements KakaoLoginService{

	@Value("${toPet.back.address}")
	private String backAddress;
	
	@Override
	public Map<String, Object>  kakaoLogin(String code) throws Exception {
		Map userInfoMap = new HashMap<>();
		/*로그인 성공 여부 + 카카오 엑세스 토큰*/
		//////////////////////////////////////////////////////////////////////
		RestTemplate rt = new RestTemplate();	
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "3494afad7131fc9645ae9b08ed0dfda6");
		params.add("redirect_uri", backAddress+"api/kakaoLogin/OAuth");
		params.add("code", code);
		
		
		
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

			
		// POST 방식으로 Http 요청한다. 그리고 response 변수의 응답 받는다.
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token", // https://{요청할 서버 주소}
				HttpMethod.POST, // 요청할 방식
				kakaoTokenRequest, // 요청할 때 보낼 데이터
				String.class // 요청 시 반환되는 데이터 타입
		);
		
		
		System.out.println(1);
		log.info(response.toString());
		ObjectMapper objMapper = new ObjectMapper();
		OAuthToken oAuthToken = null;
		try {
			oAuthToken = objMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		log.info("카카오엑세스토큰 : " + oAuthToken.getAccess_token());
		
		
		/////////////////////////////////////////////////////////////////////////
		/*로그인 성공 여부 + 카카오 엑세스 토큰*/
		
		/////////////////////////////////////////////////////////////////////////
		/*카카오 엑세스 토큰으로 회원 정보*/
		
		RestTemplate rtMemberInfo = new RestTemplate();	//회원정보 요청하는
		
		HttpHeaders headersMemberInfo = new HttpHeaders();
		headersMemberInfo.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		
		MultiValueMap<String, String> paramsMemberInfo = new LinkedMultiValueMap<>();
		paramsMemberInfo.add("grant_type", "authorization_code");
		paramsMemberInfo.add("client_id", "3494afad7131fc9645ae9b08ed0dfda6");
		paramsMemberInfo.add("redirect_uri", backAddress+"api/kakaoLogin/OAuth");
		paramsMemberInfo.add("code", code);
		
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequestMemberInfo = new HttpEntity<>(paramsMemberInfo, headersMemberInfo);

			
		// POST 방식으로 Http 요청한다. 그리고 response 변수의 응답 받는다.
		ResponseEntity<String> responseMemberInfo = rt.exchange(
				"https://kauth.kakao.com/v2/user/me", // https://{요청할 서버 주소}
				HttpMethod.POST, // 요청할 방식
				kakaoTokenRequestMemberInfo, // 요청할 때 보낼 데이터
				String.class // 요청 시 반환되는 데이터 타입
		);
		
		/*카카오 엑세스 토큰으로 회원 정보*/		
		/////////////////////////////////////////////////////////////////////////
        HashMap<String, Object> userInfo = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            // 요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + oAuthToken.getAccess_token());

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            System.out.println("response body : " + result.toString());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(result.toString());

            JsonNode properties = rootNode.path("properties");
            JsonNode kakaoAccount = rootNode.path("kakao_account");

            String nickname = properties.path("nickname").asText();
            String email = kakaoAccount.path("email").asText();
            long kakaoId = rootNode.path("id").asLong();
            
            //userInfo.put("nickname", nickname);
            //userInfo.put("email", email);
            
            
            userInfoMap.put("nickname", nickname);
            userInfoMap.put("email", email);
            userInfoMap.put("kid", kakaoId);
        } catch (IOException e) {
            e.printStackTrace();
        }

        
		
		return userInfoMap;
	}

}
