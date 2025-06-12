package com.aparttime.notification.dto;

import com.aparttime.notification.NotificationType;
import java.util.Map;

public record NotificationMessage(
    String type,
    String title,
    String content,
    String redirectUrl,
    Map<String, Object> data
) {

    public static NotificationMessage of(
        NotificationType type,
        String content,
        Map<String, Object> data
    ) {
        return new NotificationMessage(
            type.name(),
            type.getTitle(),
            content,
            type.getRedirectUrl(),
            data
        );
    }

}
