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
            
            if(sessionData != null) {
            	if(sessionData instanceof SessionMember) {
                    	 SessionMember sessionMember = (SessionMember) sessionData;
                         return sessionMember;
            	}
            	
            }
        }
        return null; 
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
