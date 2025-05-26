package com.aparttime.common;

import org.springframework.http.HttpStatus;

public record ApiResponse<T>(
    int code,
    String status,
    String message,
    T data
) {

    public static <T> ApiResponse<T> of(
        HttpStatus status,
        ResponseMessage message,
        T data
    ) {
        return new ApiResponse<>(
            status.value(),
            status.getReasonPhrase(),
            message.getMessage(),
            data
        );
    }

    public static <T> ApiResponse<T> ok(
        ResponseMessage message,
        T data
    ) {
        return new ApiResponse<>(
            HttpStatus.OK.value(),
            HttpStatus.OK.getReasonPhrase(),
            message.getMessage(),
            data
        );
    }

    public static <T> ApiResponse<T> created(
        ResponseMessage message,
        T data
    ) {
        return new ApiResponse<>(
            HttpStatus.CREATED.value(),
            HttpStatus.CREATED.getReasonPhrase(),
            message.getMessage(),
            data
        );
    }

}
