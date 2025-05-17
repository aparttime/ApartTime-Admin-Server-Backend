package com.aparttime.auth.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequest {

    private String username;
    private String password;

    public SignupRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
