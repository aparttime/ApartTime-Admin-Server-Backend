package com.aparttime.websocket.handler;

import static com.aparttime.common.constants.WebSocketConstants.*;

import com.aparttime.websocket.principal.StompPrincipal;
import com.aparttime.websocket.service.WebSocketService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompSessionEventHandler {

    private final WebSocketService webSocketService;

    @EventListener
    public void handleConnect(
        SessionConnectEvent event
    ) {
        log.info(">>> STOMP Session Connected Event Received");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        StompPrincipal principal = (StompPrincipal) accessor.getUser();

        if (principal == null) {
            // TODO: 커스텀 예외 처리 필요
            log.warn("Unauthenticated user STOMP session detected!!");
            return;
        }

        String memberId = principal.getName();
        String sessionId = accessor.getSessionId();

        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();

        String ipAddress = (String) sessionAttributes.get(IP_ADDRESS);

        long connectedAt = System.currentTimeMillis();
        sessionAttributes.put(CONNECTED_AT, connectedAt);
        sessionAttributes.put(LAST_PONG_TIME, connectedAt);

        webSocketService.saveSessionInfo(
            memberId,
            sessionId,
            ipAddress,
            connectedAt
        );

        log.info("STOMP session processed for connection: memberId={}, sessionId={}", memberId, sessionId);
    }

    @EventListener
    public void handleDisconnect(
        SessionDisconnectEvent event
    ) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();

        if (sessionId == null) {
            return;
        }

        webSocketService.deleteSessionInfo(sessionId);

        if (accessor.getUser() != null) {
            log.info("STOMP session disconnected and cleaned up: memberId={}, sessionId={}", accessor.getUser().getName(), sessionId);
        } else {
            log.info("STOMP session disconnected and cleaned up: sessionId={}", sessionId);
        }
    }

}
