package com.aparttime.exception.jwt;

import static com.aparttime.exception.ErrorCode.*;

import com.aparttime.exception.RestApiException;

public class ExpiredSecondaryTokenException extends RestApiException {

    public ExpiredSecondaryTokenException() {
        super(SECONDARY_TOKEN_EXPIRED);

    }
}
