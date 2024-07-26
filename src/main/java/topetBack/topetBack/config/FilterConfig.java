package topetBack.topetBack.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import topetBack.topetBack.filter.LoginFilter;

@Configuration
public class FilterConfig {

    private final SessionManager sessionManager; // SessionManager 필드 추가

    public FilterConfig(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
	
    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilter() {
        FilterRegistrationBean<LoginFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoginFilter(sessionManager));
        registrationBean.addUrlPatterns("/*"); // 모든 URL에 Filter 적용
        return registrationBean;
    }
}