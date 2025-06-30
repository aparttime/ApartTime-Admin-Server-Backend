package com.aparttime.websocket.principal;

import java.security.Principal;

public record StompPrincipal(
    Long memberId
) implements Principal {

    @Override
    public String getName() {
        return String.valueOf(memberId);
    }

}
