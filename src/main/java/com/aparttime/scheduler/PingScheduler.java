package com.aparttime.scheduler;

import static com.aparttime.common.constants.WebSocketConstants.*;

import com.aparttime.config.properties.ServerProperties;
import com.aparttime.websocket.dto.SessionInfo;
import com.aparttime.websocket.service.WebSocketService;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PingScheduler {

    private final WebSocketService webSocketService;
    private final ServerProperties serverProperties;
    private final SimpMessagingTemplate messagingTemplate;
    private final ScheduledExecutorService watchdogExecutor;


    @Scheduled(fixedDelay = PING_INTERVAL)
    public void sendPingToActiveSessions() {
        String serverId = serverProperties.getId();

        Set<String> membersOnServer = webSocketService.getMembersOnServer(serverId);

        log.info(">>> Server [{}] send PING to {}", serverId, membersOnServer);

        for (String memberId : membersOnServer) {
            SessionInfo sessionInfo = webSocketService.getSessionInfo(memberId);

            if (sessionInfo == null) {
                continue;
            }

            final String lastPongTime = sessionInfo.lastPongTime();

            messagingTemplate.convertAndSendToUser(
                memberId,
                PING_DESTINATION,
                PING
            );

            watchdogExecutor.schedule(() -> {
                webSocketService.checkStaleSession(memberId, lastPongTime);
            }, PONG_CHECK_DELAY, TimeUnit.MILLISECONDS);
        }

    }

}
