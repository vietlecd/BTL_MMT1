package com.project.btl_mmt1.components;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CookieUtil {
    private static final String TOKEN_COOKIE_NAME = "token";
    private static final int COOKIE_EXPIRATION = 60 * 60 * 13; // 13 hours

    public static String getTokenCookieName(HttpServletRequest request) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> TOKEN_COOKIE_NAME.equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
    public static void setTokenCookie(String token, HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from(TOKEN_COOKIE_NAME, token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(COOKIE_EXPIRATION)
                .sameSite("None")
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public static void deleteTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(TOKEN_COOKIE_NAME, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Hết hạn ngay lập tức
        response.addCookie(cookie);
    }

    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
