package com.aparttime.websocket.repository;

import static com.aparttime.common.constants.RedisConstants.*;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WebSocketSessionRepository {

    private final StringRedisTemplate redisTemplate;

    public void saveSession(
        Long memberId,
        String sessionId,
        Duration ttl
    ) {
        String key = REDIS_WEB_SOCKET_PREFIX + memberId;
        redisTemplate.opsForValue().set(key, sessionId, ttl);
    }

}
