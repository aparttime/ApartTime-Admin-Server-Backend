package com.aparttime.notification.assembler;

import static com.aparttime.common.constants.NotificationConstants.*;

import com.aparttime.annotation.NotificationPolicy;
import com.aparttime.notification.NotificationType;
import com.aparttime.notification.dto.NotificationMessage;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
@NotificationPolicy(NotificationType.MEMBER_SIGNUP)
public class MemberSignupNotificationAssembler implements NotificationAssembler {

    @Override
    public NotificationMessage assemble(
        Map<String, Object> data
    ) {

        String username = data.get(USERNAME).toString();
        String content = MEMBER_SIGNUP_PREFIX + username;

        return NotificationMessage.of(
            NotificationType.MEMBER_SIGNUP,
            content,
            Map.of(USERNAME, username)
        );

    }
}
