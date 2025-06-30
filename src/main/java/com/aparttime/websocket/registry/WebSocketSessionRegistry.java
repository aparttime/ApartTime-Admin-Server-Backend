package com.aparttime.websocket.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class WebSocketSessionRegistry {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void registerSession(
        String sessionId,
        WebSocketSession session
    ) {
        sessions.put(sessionId, session);
    }

    public void removeSession(
        String sessionId
    ) {
        sessions.remove(sessionId);
    }

    public WebSocketSession getSession(
        String sessionId
    ) {
        return sessions.get(sessionId);
    }

    public Map<String, WebSocketSession> getAllSessions() {
        return sessions;
    }

}
