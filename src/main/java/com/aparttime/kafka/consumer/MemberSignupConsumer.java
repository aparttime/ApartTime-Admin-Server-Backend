package com.aparttime.kafka.consumer;

import com.aparttime.kafka.event.MemberSignupEvent;
import com.aparttime.sse.service.SseEmitterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberSignupConsumer {

    private final ObjectMapper objectMapper;
    private final SseEmitterService sseEmitterService;

    @KafkaListener(topics = "member-signup", groupId = "admin-dashboard-group")
    public void listen(
        String message
    ) {
        try {
            MemberSignupEvent event = objectMapper.readValue(message, MemberSignupEvent.class);
            log.info("Kafka 메시지 수신: {}", message);
            sseEmitterService.send(event);
        } catch (Exception e) {
            log.error("Kafka 메시지 파싱 에러: ", e);
        }
    }

}
