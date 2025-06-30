package com.aparttime.websocket.repository;

import static com.aparttime.common.constants.RedisConstants.*;

import com.aparttime.config.properties.ServerProperties;
import com.aparttime.websocket.dto.SessionInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WebSocketRepository {

    private final StringRedisTemplate redisTemplate;
    private final ServerProperties serverProperties;
    private final ObjectMapper objectMapper;

    private String getSessionDetailKey(
        String sessionId
    ) {
        return REDIS_SESSION_PREFIX + sessionId;
    }

    private String getUserSessionsKey(
        String memberId
    ) {
        return REDIS_USER_SESSIONS_PREFIX + memberId;
    }

    public void createSession(
        SessionInfo sessionInfo
    ) {
        String sessionId = sessionInfo.sessionId();
        String memberId = sessionInfo.memberId();

        String sessionDetailKey = getSessionDetailKey(sessionId);
        String userSessionsKey = getUserSessionsKey(memberId);

        Map<String, String> sessionInfoMap = objectMapper.convertValue(
            sessionInfo,
            Map.class
        );

        redisTemplate.opsForHash().putAll(sessionDetailKey, sessionInfoMap);
        redisTemplate.opsForSet().add(userSessionsKey, sessionId);
    }

    public SessionInfo getSessionInfo(
        String sessionId
    ) {
        String sessionDetailKey = getSessionDetailKey(sessionId);
        Map<Object, Object> sessionInfo = redisTemplate.opsForHash().entries(sessionDetailKey);
        if (sessionInfo.isEmpty()) {
            return null;
        }
        return objectMapper.convertValue(sessionInfo, SessionInfo.class);
    }

    public void deleteSession(
        String sessionId
    ) {
        SessionInfo sessionInfo = getSessionInfo(sessionId);
        if (sessionInfo == null) {
            return;
        }

        String memberId = sessionInfo.memberId();
        String userSessionsKey = getUserSessionsKey(memberId);
        String sessionDetailKey = getSessionDetailKey(sessionId);

        redisTemplate.opsForSet().remove(userSessionsKey, sessionId);
        redisTemplate.delete(sessionDetailKey);
    }

}
