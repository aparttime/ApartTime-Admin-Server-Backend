package com.aparttime.admin.dto.response;

public record AdminLoginResponse(
    Long adminId,
    String username,
    String role
) {

}
