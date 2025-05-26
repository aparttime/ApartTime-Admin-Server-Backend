package com.aparttime.sse.controller;

import com.aparttime.sse.service.SseEmitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class SseController {

    private final SseEmitterService sseEmitterService;

    @GetMapping("/sse/notification")
    public SseEmitter subscribe() {
        return sseEmitterService.subscribe();
    }

}
