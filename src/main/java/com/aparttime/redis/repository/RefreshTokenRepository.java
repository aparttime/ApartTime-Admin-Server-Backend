package com.aparttime.redis.repository;

import static com.aparttime.common.constants.RedisConstants.*;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

    private final StringRedisTemplate stringRedisTemplate;

    public void save(
        Long memberId,
        String refreshToken,
        long expiration
    ) {
        String key = REDIS_REFRESH_TOKEN_PREFIX + memberId;
        Duration ttl = Duration.ofMillis(expiration);
        stringRedisTemplate.opsForValue().set(key, refreshToken, ttl);
    }

    public String get(
        Long memberId
    ) {
        return stringRedisTemplate.opsForValue().get(REDIS_REFRESH_TOKEN_PREFIX + memberId);
    }

    public void delete(
        Long memberId
    ) {
        stringRedisTemplate.delete(REDIS_REFRESH_TOKEN_PREFIX + memberId);
    }

}
