package topetBack.topetBack.config;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.SessionMember;

@Component
@Slf4j
@Service
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "SessionId";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public String create(Member object , HttpServletResponse resp) {
        log.info("session create");
        String sessionId = UUID.randomUUID().toString();
        
        redisTemplate.opsForValue().set(sessionId, object.toSessionMember());
        System.out.println("여기4");
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

    @Transactional
    public SessionMember getSessionObject(HttpServletRequest req) throws JsonMappingException, JsonProcessingException {
        log.info("session getSessionObject");
        Cookie sessionCookie = findCookie(req, SESSION_COOKIE_NAME);

        if (sessionCookie != null) {
            String sessionId = sessionCookie.getValue();
            System.out.println("sessionManager에서 쿠키를 통해 찾은 sessionId : " + sessionId);
           
            SessionMember sessionData = (SessionMember) redisTemplate.opsForValue().get(sessionId);
            System.out.println("여기는 1");
            System.out.println(sessionData.toString());
            
            if(sessionData != null) {
            	System.out.println("여기는 2");
            	if(sessionData instanceof SessionMember) {
            		 
            		 System.out.println("여기는 3");
//                     try {
                    	 System.out.println("여기는 4");
                    	 Gson gson = new Gson();
                    	 
                    	 
                    	 
                    	 SessionMember sessionMember = (SessionMember) sessionData;
//                         SessionMember sessionMember = objectMapper.readValue((String)sessionData, SessionMember.class);
                         System.out.println("여기는 5");
//                         log.info("SessionManager: Session member found: {}", sessionMember);
                         return sessionMember;
//                     } catch (JsonProcessingException e) {
//                         log.error("SessionManager: Error deserializing session data: {}", e.getMessage());
//                         return null;
//                     }
            	}
            	
            }
        }
        
        return null; // 쿠키가 없을 경우나 역직렬화 실패 시 null 반환
    }
//    @Transactional
//    public Object getSessionObject(HttpServletRequest req) {
//
//    	 log.info("session getSessionObject");
//    	    Cookie sessionCookie = findCookie(req, SESSION_COOKIE_NAME);
//    	    if (sessionCookie != null) {
//    	        String sessionId = sessionCookie.getValue();
//    	        System.out.println("sessionManager에서 쿠키를 통해 찾은 sessionId : " + sessionId);
//    	        
//    	        
//    	        Object sessionData = redisTemplate.opsForValue().get(sessionId);
//    	        ObjectMapper objMapper = new ObjectMapper();
//    	        try {
//    	        	Member sessionMember = objMapper.readValue((String) sessionData , Member.class);
//    	        	System.out.println("sessionData : " + sessionMember);
//    	        	return sessionMember;
//    	        }catch(Exception E){
//    	        	E.printStackTrace();
//    	        	return null;
//    	        }
//    	        
//    	        // Redis에서 데이터를 가져옴
//    	        
////    	        System.out.println(redisTemplate.opsForValue().get(sessionId).toString());
//    	        
//	        	
////	        	if (sessionData instanceof Member) {
////	        		return sessionData;
////	        	}else {
////	        		return null;
////	        	}
//
////    	        if (sessionData instanceof Member) {
////    	            ObjectMapper mapper = new ObjectMapper();
////    	            try {
////    	            	Member sessionMember = mapper.readValues((Member)sessionData, Member.class);/
////                        System.out.println("SessionManager: Found session member: " + sessionMember);
////                        return sessionMember;
////    	            } catch (JsonProcessingException e) {
////    	                e.printStackTrace();
////    	                // 예외 처리: JSON 문자열을 Member 객체로 변환하는 데 실패한 경우
////    	            }
////    	        } else {
////    	            System.out.println("Redis에서 가져온 데이터의 유형이 JSON 문자열이 아닙니다.");
////    	        }
//
////    	        return null; // 변환 실패 시 null 반환
//    	    }
//    	    return null; // 쿠키가 없을 경우 null 반환
//    	}
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
