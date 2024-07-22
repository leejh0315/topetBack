package topetBack.topetBack.config;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.member.domain.Member;

@Component
@Slf4j
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "SessionId";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public String create(Object object, HttpServletResponse resp) {
        log.info("session create");
        String sessionId = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(sessionId, object);
        redisTemplate.expire(sessionId, 30, TimeUnit.MINUTES); // 예시: 세션 만료 시간 설정 (30분)

        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        cookie.setMaxAge(30 * 60); // 세션 만료 시간 설정 (초 단위)
        cookie.setPath("/"); // 쿠키 경로 설정
        resp.addCookie(cookie);
        return sessionId;
        //key value
    }

    public void remove(HttpServletRequest req) {
        log.info("session remove");
        Cookie cookie = findCookie(req, SESSION_COOKIE_NAME);
        if (cookie != null) {
            redisTemplate.delete(cookie.getValue());
        }
    }

    public Object getSessionObject(HttpServletRequest req) {
//        log.info("session getSessionObject");
//        Cookie cookie = findCookie(req, SESSION_COOKIE_NAME);
//        if (cookie != null) {
//            return redisTemplate.opsForValue().get(cookie.getValue());
//        }
    	 log.info("session getSessionObject");
    	    Cookie sessionCookie = findCookie(req, SESSION_COOKIE_NAME);
    	    if (sessionCookie != null) {
    	        String sessionId = sessionCookie.getValue();
    	        System.out.println("sessionManager에서 쿠키를 통해 찾은 sessionId : " + sessionId);

    	        // Redis에서 데이터를 가져옴
    	        Object sessionData = redisTemplate.opsForValue().get(sessionId);
	        	System.out.println("sessionData : " + sessionData);
	        	if (sessionData instanceof Member) {
	        		return sessionData;
	        	}else {
	        		return null;
	        	}

//    	        if (sessionData instanceof Member) {
//    	            ObjectMapper mapper = new ObjectMapper();
//    	            try {
//    	            	Member sessionMember = mapper.readValues((Member)sessionData, Member.class);/
//                        System.out.println("SessionManager: Found session member: " + sessionMember);
//                        return sessionMember;
//    	            } catch (JsonProcessingException e) {
//    	                e.printStackTrace();
//    	                // 예외 처리: JSON 문자열을 Member 객체로 변환하는 데 실패한 경우
//    	            }
//    	        } else {
//    	            System.out.println("Redis에서 가져온 데이터의 유형이 JSON 문자열이 아닙니다.");
//    	        }

//    	        return null; // 변환 실패 시 null 반환
    	    }
    	    return null; // 쿠키가 없을 경우 null 반환
    	}
    public Cookie findCookie(HttpServletRequest req, String cookieName) {
        log.info("session findCookie");
        if (req.getCookies() != null) {
            for (Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie;
                }
            }
        }
        return null;
    }
}
