package topetBack.topetBack.config;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SessionManager {

	public static final String SESSION_COOKIE_NAME = "SessionId";
	
	
	
	private Map<String, Object> sessionMap = new HashMap<String, Object>();
	
	 @Autowired
	 private RedisTemplate<String, Object> redisTemplate;
	

	public void create(Object object, HttpServletResponse resp) {
		log.info("session create");
		String sessionId = UUID.randomUUID().toString();
		sessionMap.put(sessionId, object);

		Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
		resp.addCookie(cookie);
	}

	public void remove(HttpServletRequest req) {
		log.info("session remove");
		Cookie cookie = findCookie(req, SESSION_COOKIE_NAME);
		if(cookie != null) {
			sessionMap.remove(cookie.getValue());
		}
	}

	public Object getSessionObject(HttpServletRequest req) {
		log.info("session getSessionObject");
		Cookie cookie = findCookie(req, SESSION_COOKIE_NAME);
		if(cookie != null) {
			return sessionMap.get(cookie.getValue());
		}
		return null;
	}

	public Cookie findCookie(HttpServletRequest req, String cookieName) {
		log.info("session findCookie");
		if(req.getCookies() != null) {
			for(Cookie cookie : req.getCookies()) {
				if(cookie.getName().equals(cookieName)) {
					return cookie;
				}
			}
		}
		return null;
	}
}
