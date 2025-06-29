package com.aparttime.websocket.listener;

import com.aparttime.websocket.principal.StompPrincipal;
import com.aparttime.websocket.registry.WebSocketSessionRegistry;
import com.aparttime.websocket.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompSessionEventHandler {

    private final WebSocketSessionRegistry sessionRegistry;
    private final WebSocketService webSocketService;

    @EventListener
    public void handleConnect(
        SessionConnectEvent event
    ) {
        log.info(">>> WebSocketEventListener handleConnect()");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        StompPrincipal principal = (StompPrincipal) accessor.getUser();

        if (principal == null) {
            log.warn("Unauthenticated user tried to connect via WebSocket!!!");
            return;
        }

        String memberId = principal.getName();
        String sessionId = accessor.getSessionId();
        WebSocketSession session = (WebSocketSession) accessor.getHeader("simpSession");

        if (session != null) {
            long connectedAt = System.currentTimeMillis();
            sessionRegistry.registerSession(sessionId, session);
            session.getAttributes().put("connectedAt", connectedAt);
            session.getAttributes().put("lastPongTime", connectedAt);

            String ipAddress = (String) accessor.getSessionAttributes().get("ipAddress");
            webSocketService.saveSessionInfo(memberId, sessionId, ipAddress, connectedAt);

            log.info("Client connected: memberId={}, sessionId={}", principal.getName(), sessionId);
        }
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

        sessionRegistry.removeSession(sessionId);
        webSocketService.deleteSessionInfo(sessionId);


    }

}
