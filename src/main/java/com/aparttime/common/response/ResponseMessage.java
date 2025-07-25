package com.aparttime.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

    // Auth
    LOGIN_SUCCESS("로그인 성공"),
    SIGNUP_SUCCESS("회원가입 성공"),
    LOGOUT_SUCCESS("로그아웃 성공"),
    SECONDARY_TOKEN_ISSUE_SUCCESS("Secondary Token 발급 성공"),
    REISSUE_SUCCESS("Access Token 재발급 성공");

    private final String message;

}
