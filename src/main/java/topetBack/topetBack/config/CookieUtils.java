package topetBack.topetBack.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class CookieUtils {

    private static final String SESSION_COOKIE_NAME = "SessionId";

    public static Cookie findCookieFromRequest(HttpServletRequest req) {
        if (req.getCookies() != null) {
            for (Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals(SESSION_COOKIE_NAME)) {
                    return cookie;
                }
            }
        }
        return null;
    }
}

