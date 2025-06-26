package com.aparttime.websocket.controller;

import com.aparttime.websocket.principal.StompPrincipal;
import com.aparttime.websocket.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PongController {

    private final WebSocketService webSocketService;

    @MessageMapping("/pong")
    public void handlePong(
        StompPrincipal principal
    ) {
        String memberId = principal.getName();

        log.info(">>> PongController memberId: {}", memberId);

        webSocketService.updateLastPongTime(memberId);
    }

}
