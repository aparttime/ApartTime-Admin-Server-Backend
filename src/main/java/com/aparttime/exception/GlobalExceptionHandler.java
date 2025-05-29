package com.aparttime.exception;

import com.aparttime.exception.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<ErrorResponse> handleRestApiException(
        RestApiException exception
    ) {
        ErrorResponse errorResponse = ErrorResponse.of(
            exception.getStatus(),
            exception.getClass().getSimpleName(),
            exception.getMessage()
        );

        return ResponseEntity
            .status(exception.getStatus())
            .body(errorResponse);
    }

}
