package com.aparttime.exception;

import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Auth
    INVALID_PASSWORD(BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    // Member
    MEMBER_NOT_FOUND(BAD_REQUEST, "존재하지 않는 사용자입니다.");

    private final HttpStatus status;
    private final String message;

}
