package com.aparttime.auth.dto.response;

public record SecondaryTokenResponse(
    Long memberId,
    String secondaryToken
) {

    public static SecondaryTokenResponse of(
        Long memberId,
        String secondaryToken
    ) {
        return new SecondaryTokenResponse(
            memberId,
            secondaryToken
        );
    }
}
