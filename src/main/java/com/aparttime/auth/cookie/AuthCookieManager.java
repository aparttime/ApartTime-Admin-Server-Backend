package com.aparttime.auth.cookie;

import static com.aparttime.common.constants.CookieConstants.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthCookieManager {

    public void addRefreshToken(
        HttpServletResponse response,
        String refreshToken
    ) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, refreshToken);

        cookie.setHttpOnly(REFRESH_TOKEN_COOKIE_HTTP_ONLY);
        cookie.setSecure(REFRESH_TOKEN_COOKIE_SECURE); // HTTPS 환경에서만 전송
        cookie.setPath(REFRESH_TOKEN_COOKIE_PATH);
        cookie.setMaxAge(REFRESH_TOKEN_COOKIE_EXPIRATION_SECONDS);

        response.addCookie(cookie);
    }

    public void removeRefreshToken(
        HttpServletResponse response
    ) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, null);

        cookie.setHttpOnly(REFRESH_TOKEN_COOKIE_HTTP_ONLY);
        cookie.setSecure(REFRESH_TOKEN_COOKIE_SECURE); // HTTPS 환경에서만 전송
        cookie.setPath(REFRESH_TOKEN_COOKIE_PATH);
        cookie.setMaxAge(REFRESH_TOKEN_COOKIE_INSTANT_EXPIRE);

        response.addCookie(cookie);
    }

    public String extractRefreshToken(
        HttpServletRequest request
    ) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (REFRESH_TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

}
