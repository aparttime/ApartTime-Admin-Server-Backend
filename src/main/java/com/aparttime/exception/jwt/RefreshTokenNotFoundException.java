package com.aparttime.exception.jwt;

import static com.aparttime.exception.ErrorCode.*;

import com.aparttime.exception.RestApiException;

public class RefreshTokenNotFoundException extends RestApiException {

    public RefreshTokenNotFoundException() {
        super(REFRESH_TOKEN_NOT_FOUND);
    }
}
