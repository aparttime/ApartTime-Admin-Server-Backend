package com.aparttime.notification;

import com.aparttime.notification.assembler.NotificationAssembler;
import com.aparttime.notification.assembler.NotificationAssemblerRegistry;
import com.aparttime.notification.dto.NotificationMessage;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationAssemblerRegistry registry;
    private final SimpMessagingTemplate messagingTemplate;

    public void send(
        NotificationType type,
        Map<String, Object> data
    ) {
        NotificationAssembler assembler = registry.getAssembler(type);
        NotificationMessage message = assembler.assemble(data);

        log.info(">>> NotificationService send() message: {}", message);

        messagingTemplate.convertAndSend(
            type.getDestination(),
            message
        );
    }

}
