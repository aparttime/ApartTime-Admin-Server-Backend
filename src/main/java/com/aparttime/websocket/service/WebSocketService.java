package com.aparttime.websocket.service;

import com.aparttime.config.properties.ServerProperties;
import com.aparttime.websocket.dto.SessionInfo;
import com.aparttime.websocket.repository.WebSocketRepository;
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

    public void handleSessionConnect(
        String memberId,
        String sessionId,
        String ipAddress
    ) {
        long now = System.currentTimeMillis();

        SessionInfo sessionInfo = SessionInfo.of(
            serverProperties.getId(),
            sessionId,
            String.valueOf(now),
            ipAddress,
            String.valueOf(now)
        );

        webSocketRepository.createSession(memberId, sessionInfo);
    }

    public void updateLastPongTime(
        String memberId
    ) {
        webSocketRepository.updateLastPongTime(
            memberId,
            System.currentTimeMillis()
        );
    }

    public void checkStaleSession(
        String memberId,
        String pongTimeBeforePing
    ) {
        SessionInfo currentSessionInfo = webSocketRepository.getSessionInfo(memberId);

        if (currentSessionInfo == null) {
            return;
        }

        String pongTimeAfterPing = currentSessionInfo.lastPongTime();

        if (pongTimeBeforePing.equals(pongTimeAfterPing)) {
            log.warn("PONG response timeout for member [{}]. Disconnecting stale session.", memberId);
            logicalDisconnect(memberId);
        } else {
            log.info("PONG response received for member [{}]. Session is active.", memberId);
        }
    }

    public Set<String> getMembersOnServer(
        String serverId
    ) {
        return webSocketRepository.getMembersOnServer(serverId);
    }

    public SessionInfo getSessionInfo(
        String memberId
    ) {
        return webSocketRepository.getSessionInfo(memberId);
    }

    public void logicalDisconnect(
        String memberId
    ) {
        webSocketRepository.deleteSession(memberId);
    }

}
