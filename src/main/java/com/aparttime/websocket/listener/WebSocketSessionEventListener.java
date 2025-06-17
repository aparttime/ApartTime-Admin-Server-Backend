package com.aparttime.websocket.listener;

import static com.aparttime.common.constants.WebSocketConstants.*;

import com.aparttime.websocket.service.WebSocketSessionService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketSessionEventListener {

    private final WebSocketSessionService sessionService;

    @EventListener
    public void handleConnect(SessionConnectEvent event) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();

        if (sessionAttributes == null || !sessionAttributes.containsKey(SESSION_MEMBER_ID)) {
            log.warn("WebSocket 연결 시 필요한 memberId가 존재하지 않습니다!");
            return;
        }

        Long memberId = (Long) sessionAttributes.get(SESSION_MEMBER_ID);
        String sessionId = accessor.getSessionId();

        if (sessionId == null) {
            log.warn("WebSocket 연결 시 필요한 sessionId가 존재하지 않습니다!");
            return;
        }

        sessionService.handleSessionConnect(memberId, sessionId);
    }

}
