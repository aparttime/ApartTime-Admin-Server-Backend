package com.aparttime.exception.jwt;

import static com.aparttime.exception.ErrorCode.*;

import com.aparttime.exception.RestApiException;

public class UnsupportedTokenException extends RestApiException {

    public UnsupportedTokenException() {
        super(UNSUPPORTED_TOKEN);
    }
}
