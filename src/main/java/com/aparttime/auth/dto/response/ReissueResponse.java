package com.aparttime.auth.dto.response;

public record ReissueResponse(
    Long memberId,
    String username,
    String accessToken
) {

}
