package com.aparttime.common.constants;

public final class WebSocketConstants {

    private WebSocketConstants() {
        throw new UnsupportedOperationException("상수 클래스는 인스턴스화 할 수 없습니다.");
    }

    public static final String WEB_SOCKET_PATH = "/ws";

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

    // session attributes
    public static final String IP_ADDRESS = "ipAddress";
    public static final String CONNECTED_AT = "connectedAt";
    public static final String LAST_PONG_TIME = "lastPongTime";

    // ping-pong
    public static final long PING_INTERVAL = 25000L;
    public static final long WATCHDOG_INTERVAL = 10000L;
    public static final long PONG_TIMEOUT = 60000L;

    public static final String DIRECT_PREFIX = "/direct";
    public static final String PUB_PREFIX = "/pub";
    public static final String DIRECT_PING = "/direct/ping";
    public static final String DIRECT_NOTIFICATIONS = "/direct/notifications";
    public static final String PING = "PING";
}
