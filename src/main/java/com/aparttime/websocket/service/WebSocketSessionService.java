package com.aparttime.websocket.service;

import com.aparttime.config.properties.JwtProperties;
import com.aparttime.websocket.repository.WebSocketSessionRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketSessionService {

    private final JwtProperties jwtProperties;
    private final WebSocketSessionRepository sessionRepository;

    public void handleSessionConnect(
        Long memberId,
        String sessionId
    ) {
        Duration ttl = Duration.ofMillis(jwtProperties.getAccessTokenExpiration());
        sessionRepository.saveSession(
            memberId,
            sessionId,
            ttl
        );
    }

}
