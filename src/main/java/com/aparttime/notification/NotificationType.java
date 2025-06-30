package com.aparttime.notification;

import static com.aparttime.common.constants.NotificationConstants.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {

    MEMBER_SIGNUP(
        "신규 회원가입",
        "신규 회원가입 요청 - ",
        "/members/signup/pending",
        "/queue/notifications/signup"
    );


    private final String title;
    private final String contentPrefix;
    private final String redirectUrl;
    private final String destination;

}
