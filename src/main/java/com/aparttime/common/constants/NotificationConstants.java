package com.aparttime.common.constants;

public final class NotificationConstants {

    private NotificationConstants() {
        throw new UnsupportedOperationException("상수 클래스는 인스턴스화 할 수 없습니다.");
    }

    // 공통
    public static final String BASE_TOPIC = "/topic/notifications";

    // 신규 회원가입
    public static final String MEMBER_SIGNUP_PREFIX = "신규 회원가입 요청 - ";
    public static final String USERNAME = "username";

}
