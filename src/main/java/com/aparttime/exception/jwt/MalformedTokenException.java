package com.aparttime.exception.jwt;

import static com.aparttime.exception.ErrorCode.*;

import com.aparttime.exception.RestApiException;

public class MalformedTokenException extends RestApiException {

    public MalformedTokenException() {
        super(MALFORMED_TOKEN);

    }
}
