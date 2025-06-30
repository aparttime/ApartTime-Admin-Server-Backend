package com.aparttime.common.constants;

public final class RedisConstants {

    private RedisConstants() {
        throw new UnsupportedOperationException("상수 클래스는 인스턴스화 할 수 없습니다.");
    }

    // refresh token prefix
    public static final String REDIS_REFRESH_TOKEN_PREFIX = "refresh:";

    // secondary token prefix
    public static final String REDIS_SECONDARY_TOKEN_PREFIX = "secondary:";

    // session hash prefix
    public static final String REDIS_SESSION_PREFIX = "ws:session:";

    // user sessions prefix
    public static final String REDIS_USER_SESSIONS_PREFIX = "ws:user-sessions:";

}
