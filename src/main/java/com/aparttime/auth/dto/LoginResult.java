package com.aparttime.auth.dto;

import com.aparttime.auth.dto.response.LoginResponse;

public record LoginResult(
    LoginResponse loginResponse,
    String refreshToken
) {

}
