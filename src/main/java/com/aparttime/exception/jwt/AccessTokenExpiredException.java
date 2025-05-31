package com.aparttime.exception.jwt;

import static com.aparttime.exception.ErrorCode.*;

import com.aparttime.exception.RestApiException;

public class AccessTokenExpiredException extends RestApiException {

    public AccessTokenExpiredException() {
        super(ACCESS_TOKEN_EXPIRED);
    }
}
