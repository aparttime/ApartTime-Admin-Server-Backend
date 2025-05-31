package com.aparttime.exception.jwt;

import static com.aparttime.exception.ErrorCode.*;

import com.aparttime.exception.RestApiException;

public class InvalidTokenException extends RestApiException {

    public InvalidTokenException() {
        super(INVALID_TOKEN);
    }
}
