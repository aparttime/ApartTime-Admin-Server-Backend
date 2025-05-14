package com.aparttime.auth.dto.request;

public record AdminLoginRequest(
    String username,
    String password
) {

}
