package com.aparttime.auth.dto.response;

public record LoginResponse(
    Long memberId,
    String username,
    String accessToken
) {

    public static LoginResponse of(
        Long memberId,
        String username,
        String accessToken
    ) {
        return new LoginResponse(
            memberId,
            username,
            accessToken
        );
    }

}
