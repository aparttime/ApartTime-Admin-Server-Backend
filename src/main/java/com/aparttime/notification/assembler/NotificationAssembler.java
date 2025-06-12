package com.aparttime.notification.assembler;

import com.aparttime.notification.dto.NotificationMessage;
import java.util.Map;

public interface NotificationAssembler {

    NotificationMessage assemble(Map<String, Object> data);
}
