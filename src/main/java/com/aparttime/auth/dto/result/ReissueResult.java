package com.aparttime.auth.dto.result;

import com.aparttime.auth.dto.response.ReissueResponse;

public record ReissueResult(
    ReissueResponse reissueResponse,
    String refreshToken
) {

    public static ReissueResult of(
        ReissueResponse reissueResponse,
        String refreshToken
    ) {
        return new ReissueResult(
            reissueResponse,
            refreshToken
        );
    }

}
