package com.aparttime.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RestApiException extends RuntimeException {

    private final HttpStatus status;

    public RestApiException(
        ErrorCode errorCode
    ) {
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
    }

}
