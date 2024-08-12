package topetBack.topetBack.config;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.member.domain.Member;
import topetBack.topetBack.member.domain.SessionMember;
import topetBack.topetBack.pet.domain.PetResponseDTO;

@Component
@Slf4j
@Service
public class SessionManager {

	public static final String SESSION_COOKIE_NAME = "SessionId";

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public String create(Member object, List<PetResponseDTO> pets, HttpServletResponse resp) {
		log.info("session create");
		String sessionId = UUID.randomUUID().toString();
		SessionMember sessionMember = object.toSessionMember();
		sessionMember.setPets(pets);
		redisTemplate.opsForValue().set(sessionId, sessionMember);
		redisTemplate.expire(sessionId, 180, TimeUnit.MINUTES); // 예시: 세션 만료 시간 설정 (30분)

		Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
		cookie.setMaxAge(180 * 60); // 세션 만료 시간 설정 (초 단위)//180분
		cookie.setPath("/"); // 쿠키 경로 설정
		resp.addCookie(cookie);
		return sessionId;
		// key value
	}

	@Transactional
	public void refreshPetAdd(PetResponseDTO petResponseDTO, HttpServletResponse resp, HttpServletRequest req) {
		log.info("session refresh");
		Cookie sessionCookie = findCookie(req, SESSION_COOKIE_NAME);
		if (sessionCookie != null) {
			String sessionId = sessionCookie.getValue();
			System.out.println("sessionManager에서 쿠키를 통해 찾은 sessionId : " + sessionId);

			SessionMember sessionData = (SessionMember) redisTemplate.opsForValue().get(sessionId);
			if (sessionData.getPets() != null) {
				sessionData.getPets().add(petResponseDTO);
			} else {
				List<PetResponseDTO> petList = new ArrayList<PetResponseDTO>();
				petList.add(petResponseDTO);
				sessionData.setPets(petList);
			}
			redisTemplate.opsForValue().set(sessionId, sessionData);
			redisTemplate.expire(sessionCookie.getValue(), 300, TimeUnit.MINUTES); // 예시: 세션 만료 시간 설정 (30분)
		}
	}

	@Transactional
	public void refreshMember(Member member, HttpServletResponse resp, HttpServletRequest req) {
		log.info("session refresh");
		Cookie sessionCookie = findCookie(req, SESSION_COOKIE_NAME);
		if (sessionCookie != null) {
			String sessionId = sessionCookie.getValue();
			SessionMember sessionMember = member.toSessionMember();
			redisTemplate.opsForValue().set(sessionId, sessionMember);
			redisTemplate.expire(sessionCookie.getValue(), 300, TimeUnit.MINUTES);
		}
	}

	@Transactional
	public SessionMember getSessionObject(HttpServletRequest req) throws JsonMappingException, JsonProcessingException {
		log.info("session getSessionObject");
		Cookie sessionCookie = findCookie(req, SESSION_COOKIE_NAME);
		System.out.println("getSessionObject sessionCookie : " + sessionCookie.getValue());
		if (sessionCookie != null) {
			String sessionId = sessionCookie.getValue();
			System.out.println("sessionManager에서 쿠키를 통해 찾은 sessionId : " + sessionId);

			SessionMember sessionData = (SessionMember) redisTemplate.opsForValue().get(sessionId);
			System.out.println(sessionData);
			if (sessionData != null) {
				if (sessionData instanceof SessionMember) {
					SessionMember sessionMember = (SessionMember) sessionData;
					return sessionMember;
				}
			}
		}
		return null;
	}

	public SessionMember getSessionMember(Cookie cookie) throws JsonMappingException, JsonProcessingException {

		String sessionId = cookie.getValue();

		SessionMember sessionData = (SessionMember) redisTemplate.opsForValue().get(sessionId);
		System.out.println(sessionData);
		if (sessionData != null) {
			if (sessionData instanceof SessionMember) {
				SessionMember sessionMember = (SessionMember) sessionData;
				return sessionMember;
			}
		}

		return null;
	}

	public SessionMember updateMember(Member member, Cookie cookie) {
		log.info("session update");
		assert cookie != null;
		String sessionId = cookie.getValue();
		System.out.println("sessionManager에서 쿠키를 통해 찾은 sessionId : " + sessionId);
		SessionMember sessionData = (SessionMember) redisTemplate.opsForValue().get(sessionId);
		assert sessionData != null;
		sessionData.setName(member.getName());
		redisTemplate.opsForValue().set(cookie.getValue(), sessionData);
		redisTemplate.expire(cookie.getValue(), 30, TimeUnit.MINUTES); // 예시: 세션 만료 시간 설정 (30분)

		return sessionData;
	}

	@Transactional
	public String remove(HttpServletRequest req) {
		log.info("session remove");
		Cookie sessionCookie = findCookie(req, SESSION_COOKIE_NAME);
		if (sessionCookie != null) {
			log.info("delete {} ", sessionCookie.getValue());
			redisTemplate.delete(sessionCookie.getValue());
			return "success";
		}
		return null;
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