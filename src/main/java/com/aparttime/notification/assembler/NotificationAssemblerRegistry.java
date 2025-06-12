package com.aparttime.notification.assembler;

import com.aparttime.annotation.NotificationPolicy;
import com.aparttime.notification.NotificationType;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class NotificationAssemblerRegistry {

    private final Map<NotificationType, NotificationAssembler> registry = new HashMap<>();
    private final List<NotificationAssembler> assemblers;

    public NotificationAssemblerRegistry(List<NotificationAssembler> assemblers) {
        this.assemblers = assemblers;
    }

    @PostConstruct
    public void initialize() {
        for (NotificationAssembler assembler : assemblers) {
            NotificationPolicy policy = assembler
                .getClass()
                .getAnnotation(NotificationPolicy.class);

            if (policy != null) {
                registry.put(policy.value(), assembler);
            }
        }
    }

    public NotificationAssembler getAssembler(
        NotificationType type
    ) {
        NotificationAssembler assembler = registry.get(type);

        if (assembler == null) {
            // TODO: 예외 처리
            throw new IllegalArgumentException("No assembler found for type: " + type);
        }

        return assembler;
    }
}
