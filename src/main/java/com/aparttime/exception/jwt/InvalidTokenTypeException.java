package com.aparttime.exception.jwt;

import static com.aparttime.exception.ErrorCode.*;

import com.aparttime.exception.RestApiException;

public class InvalidTokenTypeException extends RestApiException {

    public InvalidTokenTypeException() {
        super(INVALID_TOKEN_TYPE);
    }
}
