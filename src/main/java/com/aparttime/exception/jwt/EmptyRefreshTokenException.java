package com.aparttime.exception.jwt;

import static com.aparttime.exception.ErrorCode.*;

import com.aparttime.exception.RestApiException;

public class EmptyRefreshTokenException extends RestApiException {

    public EmptyRefreshTokenException() {
        super(EMPTY_REFRESH_TOKEN);
    }
}
