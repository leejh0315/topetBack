package topetBack.topetBack.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.PatternMatchUtils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import topetBack.topetBack.config.SessionManager;
import topetBack.topetBack.member.domain.Member;


@Slf4j
public class LoginFilter implements Filter {

	private static final String[] whiteList ={"/api/member/**", "/api/community/**", "/api/comment/**"};


    private final SessionManager sessionManager;

    public LoginFilter(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
   
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("비교");
		HttpServletRequest req = (HttpServletRequest)request;
		String uri = req.getRequestURI();
		System.out.println("uri :" + uri );
		HttpServletResponse resp = (HttpServletResponse)response;
		
		if(isLoginCheckPath(uri)) {
			HttpSession session = req.getSession(true);
			Long memberId = sessionManager.getSessionObject(req);
			System.out.println(memberId);
			log.info("Session: {}", session);
			log.info("Member: {}", memberId);
			if(memberId == null ||session == null) {
				log.info("로그인 없이 접근 시도 {}", uri);
				resp.sendRedirect("/api");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	
	private boolean isLoginCheckPath(String requestURI) {
		return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
	}
}
