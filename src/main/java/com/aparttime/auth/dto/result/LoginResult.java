package com.aparttime.auth.dto.result;

import com.aparttime.auth.dto.response.LoginResponse;

public record LoginResult(
    LoginResponse loginResponse,
    String refreshToken
) {

    public static LoginResult of(
        LoginResponse loginResponse,
        String refreshToken
    ) {
        return new LoginResult(
            loginResponse,
            refreshToken
        );
    }

}
