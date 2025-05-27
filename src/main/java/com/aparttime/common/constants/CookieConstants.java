package com.aparttime.common.constants;

public final class CookieConstants {

    private CookieConstants() {
        throw new UnsupportedOperationException("상수 클래스는 인스턴스화 할 수 없습니다.");
    }

    // 쿠키 이름
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";

    // 쿠키 속성
    public static final String REFRESH_TOKEN_COOKIE_PATH = "/";
    public static final boolean REFRESH_TOKEN_COOKIE_HTTP_ONLY = true;
    public static final boolean REFRESH_TOKEN_COOKIE_SECURE = true;

    // 쿠키 만료 시간 (7일)
    public static final int REFRESH_TOKEN_COOKIE_EXPIRATION_SECONDS = 7 * 24 * 60 * 60;

    // 쿠키 즉시 만료
    public static final int REFRESH_TOKEN_COOKIE_INSTANT_EXPIRE = 0;

}
