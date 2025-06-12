package com.aparttime.redis.repository;

import static com.aparttime.common.constants.RedisConstants.*;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SecondaryTokenRepository {

    private final StringRedisTemplate stringRedisTemplate;

    public void save(
        Long memberId,
        String secondaryToken,
        long expiration
    ) {
        String key = REDIS_SECONDARY_TOKEN_PREFIX + secondaryToken;
        Duration ttl = Duration.ofMillis(expiration);
        stringRedisTemplate.opsForValue().set(key, String.valueOf(memberId), ttl);
    }

    public Long findMemberIdBySecondaryToken(
        String secondaryToken
    ) {
        String key = REDIS_SECONDARY_TOKEN_PREFIX + secondaryToken;
        String memberId = stringRedisTemplate.opsForValue().get(key);

        if (memberId == null) {
            return null;
        }

        return Long.parseLong(memberId);
    }

    public void deleteBySecondaryToken(
        String secondaryToken
    ) {
        String key = REDIS_SECONDARY_TOKEN_PREFIX + secondaryToken;
        stringRedisTemplate.delete(key);
    }

}
