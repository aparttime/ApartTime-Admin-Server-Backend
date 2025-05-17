package com.aparttime.sse.service;

import com.aparttime.kafka.event.MemberSignupEvent;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Service
public class SseEmitterService {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        return emitter;
    }

    public void send(
        MemberSignupEvent event
    ) {
        String message = "신규 가입 요청! " + event.getUsername() + " (" + event.getEmail() + ")";
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                    .name("member-signup")
                    .data(message));
                log.info("SSE 알림 전송 완료!!");
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
    }

}
