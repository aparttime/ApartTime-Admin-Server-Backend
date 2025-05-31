package com.aparttime.exception.jwt;

import static com.aparttime.exception.ErrorCode.*;

import com.aparttime.exception.RestApiException;

public class InvalidSignatureException extends RestApiException {

    public InvalidSignatureException() {
        super(INVALID_SIGNATURE);
    }
}
