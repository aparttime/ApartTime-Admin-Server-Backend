package com.aparttime.websocket.repository;

import static com.aparttime.common.constants.RedisConstants.*;

import com.aparttime.config.properties.ServerProperties;
import com.aparttime.websocket.dto.SessionInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WebSocketRepository {

    private final StringRedisTemplate redisTemplate;
    private final ServerProperties serverProperties;
    private final ObjectMapper objectMapper;

    private String getSessionKey(
        String memberId
    ) {
        return REDIS_SESSION_HASH_PREFIX + memberId;
    }

    private String getServerMembersKey(
        String serverId
    ) {
        return REDIS_SERVERS_MEMBERS_PREFIX + serverId + REDIS_SERVERS_MEMBERS_SUFFIX;
    }

    public void createSession(
        String memberId,
        SessionInfo sessionInfo
    ) {
        String serverId = serverProperties.getId();
        String sessionKey = getSessionKey(memberId);
        String serverMembersKey = getServerMembersKey(serverId);

        Map<String, String> sessionData = objectMapper.convertValue(
            sessionInfo,
            Map.class
        );

        redisTemplate.opsForHash().putAll(sessionKey, sessionData);
        redisTemplate.opsForSet().add(serverMembersKey, memberId);
    }

    public void updateLastPongTime(
        String memberId,
        long timestamp
    ) {
        String sessionKey = getSessionKey(memberId);

        redisTemplate.opsForHash().put(
            sessionKey,
            "lastPongTime",
            String.valueOf(timestamp)
        );
    }

    public Set<String> getMembersOnServer(
        String serverId
    ) {
        String serverMembersKey = getServerMembersKey(serverId);
        return redisTemplate.opsForSet().members(serverMembersKey);
    }

    public SessionInfo getSessionInfo(
        String memberId
    ) {
        String sessionKey = getSessionKey(memberId);
        Map<Object, Object> sessionData = redisTemplate.opsForHash().entries(sessionKey);
        if (sessionData.isEmpty()) {
            return null;
        }
        return objectMapper.convertValue(sessionData, SessionInfo.class);
    }

    public void deleteSession(
        String memberId
    ) {
        SessionInfo sessionInfo = getSessionInfo(memberId);
        if (sessionInfo != null) {
            String serverId = sessionInfo.serverId();
            String serverMembersKey = getServerMembersKey(serverId);
            redisTemplate.opsForSet().remove(serverMembersKey, memberId);
        }
        String sessionKey = getSessionKey(memberId);
        redisTemplate.delete(sessionKey);
    }

}
