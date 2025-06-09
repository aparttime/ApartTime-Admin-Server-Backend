package com.aparttime.exception.jwt;

import static com.aparttime.exception.ErrorCode.*;

import com.aparttime.exception.RestApiException;

public class EmptySecondaryTokenException extends RestApiException {

    public EmptySecondaryTokenException() {
        super(EMPTY_SECONDARY_TOKEN);
    }
}
