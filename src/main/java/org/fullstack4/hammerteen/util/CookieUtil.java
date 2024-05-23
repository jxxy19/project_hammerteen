package org.fullstack4.hammerteen.util;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge); // 쿠키의 유효 기간 설정(초 단위)
        cookie.setPath("/"); // 쿠키의 경로 설정
        response.addCookie(cookie);
    }

    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null; // 해당 이름의 쿠키가 없을 경우 null 반환
    }

    public static void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null); // 삭제를 위한 쿠키 생성
        cookie.setMaxAge(0); // 쿠키의 유효 기간을 0으로 설정해 즉시 삭제되도록 함
        cookie.setPath("/"); // 쿠키의 경로 설정
        response.addCookie(cookie);
    }
}
