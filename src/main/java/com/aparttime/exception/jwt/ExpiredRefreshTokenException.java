package com.aparttime.exception.jwt;

import static com.aparttime.exception.ErrorCode.*;

import com.aparttime.exception.RestApiException;

public class ExpiredRefreshTokenException extends RestApiException {

    public ExpiredRefreshTokenException() {
        super(REFRESH_TOKEN_EXPIRED);
    }
}
