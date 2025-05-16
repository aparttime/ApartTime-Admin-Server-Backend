package com.aparttime.admin.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequest {

    private String username;
    private String password;

    public SignupRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
