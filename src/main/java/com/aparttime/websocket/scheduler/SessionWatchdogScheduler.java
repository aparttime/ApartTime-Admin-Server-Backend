package com.aparttime.websocket.scheduler;

import static com.aparttime.common.constants.WebSocketConstants.*;

import com.aparttime.websocket.registry.WebSocketSessionRegistry;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionWatchdogScheduler {

    private final WebSocketSessionRegistry sessionRegistry;

    @Scheduled(fixedRate = WATCHDOG_INTERVAL)
    public void cleanupStaleSessions() {
        log.info(">>> SessionWatchdogScheduler cleanupStaleSessions()");

        long now = System.currentTimeMillis();

        sessionRegistry.getAllSessions().forEach((sessionId, session) -> {
            if (!session.isOpen()) {
                return;
            }

            Map<String, Object> attributes = session.getAttributes();
            long lastPongTime = (long) attributes.get(LAST_PONG_TIME);

            if ((now - lastPongTime) > PONG_TIMEOUT) {
                log.warn("PONG response timeout for session [{}]. Disconnecting...", sessionId);
                closeSession(session);
            }
        });
    }

    private void closeSession(
        WebSocketSession session
    ) {
        try {
            if (session.isOpen()) {
                session.close(CloseStatus.SESSION_NOT_RELIABLE);
            }
        } catch (IOException e) {
            log.error("Error while closing session [{}]: {}", session.getId(), e.getMessage());
        }
    }

}
