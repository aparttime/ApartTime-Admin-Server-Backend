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
        String key = REDIS_REFRESH_TOKEN_PREFIX + refreshToken;
        Duration ttl = Duration.ofMillis(expiration);
        stringRedisTemplate.opsForValue().set(key, String.valueOf(memberId), ttl);
    }

    public Long findMemberIdByRefreshToken(
        String refreshToken
    ) {
        String key = REDIS_REFRESH_TOKEN_PREFIX + refreshToken;
        String memberId = stringRedisTemplate.opsForValue().get(key);

        if (memberId == null) {
            return null;
        }

        return Long.parseLong(memberId);
    }

    public void deleteByRefreshToken(
        String refreshToken
    ) {
        String key = REDIS_REFRESH_TOKEN_PREFIX + refreshToken;
        stringRedisTemplate.delete(key);
    }

}
