package com.aparttime.websocket.listener;

import com.aparttime.websocket.principal.StompPrincipal;
import com.aparttime.websocket.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final WebSocketService sessionService;
    private final WebSocketService webSocketService;

    @EventListener
    public void handleConnect(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        StompPrincipal principal = (StompPrincipal) accessor.getUser();

        if (principal == null) {
            log.warn("인증되지 않은 사용자가 WebSocket 연결을 시도했습니다!!");
            return;
        }

        String sessionId = accessor.getSessionId();
        String memberId = principal.getName();
        String ipAddress = (String) accessor.getSessionAttributes().get("ipAddress");

        log.info(">>> WebSocketEventListener WebSocket Connected: memberId={}, sessionId={}", memberId, sessionId);

        webSocketService.handleSessionConnect(
            memberId,
            sessionId,
            ipAddress
        );
    }

}
