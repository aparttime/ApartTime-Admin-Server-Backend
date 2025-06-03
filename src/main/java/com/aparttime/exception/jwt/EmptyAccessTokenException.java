package com.aparttime.exception.jwt;

import static com.aparttime.exception.ErrorCode.*;

import com.aparttime.exception.RestApiException;

public class EmptyAccessTokenException extends RestApiException {

    public EmptyAccessTokenException() {
        super(EMPTY_ACCESS_TOKEN);
    }
}
