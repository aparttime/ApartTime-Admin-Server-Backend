package com.aparttime.kafka;

import static com.aparttime.notification.NotificationType.*;

import com.aparttime.notification.NotificationService;
import com.aparttime.notification.NotificationType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaNotificationListener {

    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;

    @KafkaListener(topics = "member-signup", groupId = "admin-dashboard-group")
    public void handleMemberSignup(
        ConsumerRecord<String, String> record
    ) {
        log.info(">>> KafkaNotificationListener handleMemberSignup()");
        handle(record, MEMBER_SIGNUP);
    }

    private void handle(
        ConsumerRecord<String, String> record,
        NotificationType type
    ) {
        log.info(">>> KafkaNotificationListener handle() record: {}", record);
        try {
            Map<String, Object> data = objectMapper.readValue(
                record.value(),
                new TypeReference<>() {
                }
            );

            log.info(">>> KafkaNotificationListener handle() data: {}", data);

            notificationService.send(type, data);
        } catch (JsonProcessingException e) {
            // TODO: 예외 처리 필요
            throw new RuntimeException(e);
        }
    }
}
