package com.aparttime.exception.auth;

import static com.aparttime.exception.ErrorCode.*;

import com.aparttime.exception.RestApiException;

public class DuplicateUsernameException extends RestApiException {

    public DuplicateUsernameException() {
        super(DUPLICATE_USERNAME);
    }
}
