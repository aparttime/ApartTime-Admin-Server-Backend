package com.aparttime.websocket.controller;

import com.aparttime.websocket.service.WebSocketService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PongController {

    private final WebSocketService webSocketService;

    @MessageMapping("/pong")
    public void handlePong(
        SimpMessageHeaderAccessor accessor
    ) {
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
        webSocketService.updateLastPongTime(sessionAttributes);
    }

}
