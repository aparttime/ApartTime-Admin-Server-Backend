package com.aparttime.exception.jwt;

import static com.aparttime.exception.ErrorCode.*;

import com.aparttime.exception.RestApiException;

public class ExpiredAccessTokenException extends RestApiException {

    public ExpiredAccessTokenException() {
        super(ACCESS_TOKEN_EXPIRED);
    }
}
