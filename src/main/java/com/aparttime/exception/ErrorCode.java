package com.aparttime.exception;

import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // auth
    INVALID_PASSWORD(BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    // member
    MEMBER_NOT_FOUND(BAD_REQUEST, "존재하지 않는 사용자입니다."),

    // jwt
    ACCESS_TOKEN_EXPIRED(UNAUTHORIZED, "Access Token이 만료되었습니다."),
    EMPTY_ACCESS_TOKEN(UNAUTHORIZED, "Access Token이 존재하지 않습니다"),
    EMPTY_REFRESH_TOKEN(UNAUTHORIZED, "Refresh Token이 Cookie에 저장되어 있지 않습니다."),
    REFRESH_TOKEN_EXPIRED(UNAUTHORIZED, "Refresh Token이 만료되었습니다."),
    INVALID_SIGNATURE(UNAUTHORIZED, "JWT 서명이 유효하지 않습니다."),
    MALFORMED_TOKEN(UNAUTHORIZED, "손상되었거나 올바르지 않은 형식의 JWT입니다."),
    UNSUPPORTED_TOKEN(UNAUTHORIZED, "지원하지 않는 형식의 JWT입니다."),
    INVALID_TOKEN(UNAUTHORIZED, "유효하지 않은 Token입니다.");

    private final HttpStatus status;
    private final String message;

}
