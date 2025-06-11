package com.aparttime.notification.dto;

import java.util.Map;

public record NotificationMessage(
    String type,
    String title,
    String content,
    String redirectUrl,
    Map<String, Object> data
) {

}
