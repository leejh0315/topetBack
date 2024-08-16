package topetBack.topetBack.config;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Service
public class SessionManager {

	public static final String SESSION_COOKIE_NAME = "SessionId";

	@Autowired
	private RedisTemplate<String, Long> redisTemplate;

	public String create(Long memberId, HttpServletResponse resp) {
		log.info("session create");
		String sessionId = UUID.randomUUID().toString();
		redisTemplate.opsForValue().set(sessionId, memberId);
		redisTemplate.expire(sessionId, 180, TimeUnit.MINUTES); // 예시: 세션 만료 시간 설정 (30분)

		Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
		cookie.setMaxAge(180 * 60); // 세션 만료 시간 설정 (초 단위)//180분
		cookie.setPath("/"); // 쿠키 경로 설정
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setAttribute("SameSite", "None");
		resp.addCookie(cookie);
		return sessionId;
		// key value
	}

//	@Transactional
//	public void refreshPetAdd(PetResponseDTO petResponseDTO, HttpServletResponse resp, HttpServletRequest req) {
//		log.info("session refresh");
//		Cookie sessionCookie = findCookie(req, SESSION_COOKIE_NAME);
//		if (sessionCookie != null) {
//			String sessionId = sessionCookie.getValue();
//			System.out.println("sessionManager에서 쿠키를 통해 찾은 sessionId : " + sessionId);
//
//			SessionMember sessionData = (SessionMember) redisTemplate.opsForValue().get(sessionId);
//			if (sessionData.getPets() != null) {
//				sessionData.getPets().add(petResponseDTO);
//			} else {
//				List<PetResponseDTO> petList = new ArrayList<PetResponseDTO>();
//				petList.add(petResponseDTO);
//				sessionData.setPets(petList);
//			}
//			redisTemplate.opsForValue().set(sessionId, sessionData);
//			redisTemplate.expire(sessionCookie.getValue(), 300, TimeUnit.MINUTES); // 예시: 세션 만료 시간 설정 (30분)
//		}
//	}

//	@Transactional
//	public void refreshMember(Member member, HttpServletResponse resp, HttpServletRequest req) {
//		log.info("session refresh");
//		Cookie sessionCookie = findCookie(req, SESSION_COOKIE_NAME);
//		if (sessionCookie != null) {
//			String sessionId = sessionCookie.getValue();
//			SessionMember sessionMember = member.toSessionMember();
//			redisTemplate.opsForValue().set(sessionId, sessionMember);
//			redisTemplate.expire(sessionCookie.getValue(), 300, TimeUnit.MINUTES);
//		}
//	}

	@Transactional
	public Long getSessionObject(HttpServletRequest req)  {
		log.info("session getSessionObject");
		Cookie sessionCookie = findCookie(req, SESSION_COOKIE_NAME);
//		System.out.println("getSessionObject sessionCookie : " + sessionCookie.getValue());
		if (sessionCookie != null) {
			String sessionId = sessionCookie.getValue();
			System.out.println("sessionManager에서 쿠키를 통해 찾은 sessionId : " + sessionId);
			Object memberId = redisTemplate.opsForValue().get(sessionId);
			if (memberId != null) {
				if (memberId instanceof Integer) {
					return Long.valueOf(memberId.toString());
				}
			}
		}
		return null;
	}

//	public SessionMember getSessionMember(Cookie cookie) throws JsonMappingException, JsonProcessingException {
//		String sessionId = cookie.getValue();
//		SessionMember sessionData = (SessionMember) redisTemplate.opsForValue().get(sessionId);
//		System.out.println(sessionData);
//		if (sessionData != null) {
//			if (sessionData instanceof SessionMember) {
//				SessionMember sessionMember = (SessionMember) sessionData;
//				return sessionMember;
//			}
//		}
//		return null;
//	}
//
//	public SessionMember updateMember(Member member, Cookie cookie) {
//		log.info("session update");
//		assert cookie != null;
//		String sessionId = cookie.getValue();
//		System.out.println("sessionManager에서 쿠키를 통해 찾은 sessionId : " + sessionId);
//		SessionMember sessionData = (SessionMember) redisTemplate.opsForValue().get(sessionId);
//		assert sessionData != null;
//		sessionData.setName(member.getName());
//		redisTemplate.opsForValue().set(cookie.getValue(), sessionData);
//		redisTemplate.expire(cookie.getValue(), 30, TimeUnit.MINUTES); // 예시: 세션 만료 시간 설정 (30분)
//
//		return sessionData;
//	}

    @Transactional
    public String remove(HttpServletRequest req, HttpServletResponse resp) {
        log.info("session remove");
        // Find the session cookie
        Cookie sessionCookie = findCookie(req, SESSION_COOKIE_NAME);
        if (sessionCookie != null) {
            String sessionId = sessionCookie.getValue();
            // Delete session data from Redis
            log.info("delete {} ", sessionId);
            redisTemplate.delete(sessionId);
            // Create a new cookie to overwrite the existing one
            Cookie newCookie = new Cookie(SESSION_COOKIE_NAME, "");
            newCookie.setMaxAge(0); // Set the max age to 0 to delete the cookie
            newCookie.setPath("/"); // Set the path to match the original cookie path
            resp.addCookie(newCookie);
            return "success";
        }
        return "cookie not found";
    }

	@Transactional
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