package com.aparttime.exception.dto;

import java.util.List;
import org.springframework.http.HttpStatus;

public record ErrorResponse(
    int code,
    String status,
    List<ErrorDetail> errors
) {

    public static ErrorResponse of(
        HttpStatus status,
        List<ErrorDetail> errors
    ) {
        return new ErrorResponse(
            status.value(),
            status.getReasonPhrase(),
            errors
        );
    }

    public static ErrorResponse of(
        HttpStatus status,
        String type,
        String message
    ) {
        return new ErrorResponse(
            status.value(),
            status.getReasonPhrase(),
            List.of(new ErrorDetail(type, message))
        );
    }

}
