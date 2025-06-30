package com.aparttime.websocket.scheduler;

import static com.aparttime.common.constants.WebSocketConstants.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PingScheduler {

    private final SimpMessagingTemplate messagingTemplate;

    @Scheduled(fixedRate = PING_INTERVAL)
    public void broadcastPing() {
        messagingTemplate.convertAndSend(DIRECT_PING, PING);
    }

}
