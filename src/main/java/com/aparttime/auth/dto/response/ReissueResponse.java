package com.aparttime.auth.dto.response;

public record ReissueResponse(
    Long memberId,
    String username,
    String accessToken
) {

    public static ReissueResponse of(
        Long memberId,
        String username,
        String accessToken
    ) {
        return new ReissueResponse(
            memberId,
            username,
            accessToken
        );
    }

}
