package com.aparttime.common.constants;

public final class WebSocketConstants {

    private WebSocketConstants() {
        throw new UnsupportedOperationException("상수 클래스는 인스턴스화 할 수 없습니다.");
    }

    // division
    public static final String QUERY_SEPARATOR = "&";
    public static final String KEY_VALUE_SEPARATOR = "=";

    // index
    public static final int KEY_INDEX = 0;
    public static final int VALUE_INDEX = 1;

    // split limit
    public static final int PAIR_SIZE = 2;

    // parameter key
    public static final String PARAM_SECONDARY_TOKEN = "secondary_token";

    public static final String SESSION_MEMBER_ID = "memberId";
    public static final String WEB_SOCKET_PATH = "/ws";
    public static final String SUB_PREFIX = "/sub";
    public static final String PUB_PREFIX = "/pub";
}
