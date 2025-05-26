package com.aparttime.auth.dto.response;

public record LoginResponse(
    Long memberId,
    String username,
    String accessToken
) {

}
