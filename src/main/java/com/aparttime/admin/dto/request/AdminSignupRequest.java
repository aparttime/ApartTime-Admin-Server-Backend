package com.aparttime.admin.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminSignupRequest {

    private String username;
    private String password;

    public AdminSignupRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
