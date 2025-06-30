package com.aparttime.websocket.service;

import static com.aparttime.common.constants.WebSocketConstants.*;

import com.aparttime.common.constants.WebSocketConstants;
import com.aparttime.config.properties.ServerProperties;
import com.aparttime.websocket.dto.SessionInfo;
import com.aparttime.websocket.repository.WebSocketRepository;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketService {

    private final ServerProperties serverProperties;
    private final WebSocketRepository webSocketRepository;

    public void saveSessionInfo(
        String memberId,
        String sessionId,
        String ipAddress,
        long connectedAt
    ) {
        SessionInfo sessionInfo = SessionInfo.of(
            memberId,
            serverProperties.getId(),
            sessionId,
            String.valueOf(connectedAt),
            ipAddress
        );

        webSocketRepository.createSession(sessionInfo);
    }

    public void updateLastPongTime(
        Map<String, Object> sessionAttributes
    ) {
        sessionAttributes.put(LAST_PONG_TIME, System.currentTimeMillis());
    }

    public void deleteSessionInfo(
        String sessionId
    ) {
        log.info("Deleting session info from Redis for sessionId: [{}]", sessionId);
        webSocketRepository.deleteSession(sessionId);
    }

}
