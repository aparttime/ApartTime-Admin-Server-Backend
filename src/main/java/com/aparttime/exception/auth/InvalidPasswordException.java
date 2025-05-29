package com.aparttime.exception.auth;

import static com.aparttime.exception.ErrorCode.*;

import com.aparttime.exception.RestApiException;

public class InvalidPasswordException extends RestApiException {

    public InvalidPasswordException() {
        super(INVALID_PASSWORD);
    }
}
