package com.aparttime.auth.controller;

import static com.aparttime.common.response.ResponseMessage.*;

import com.aparttime.auth.cookie.AuthCookieManager;
import com.aparttime.auth.dto.LoginResult;
import com.aparttime.auth.dto.request.LoginRequest;
import com.aparttime.auth.dto.response.LoginResponse;
import com.aparttime.auth.dto.response.ReissueResponse;
import com.aparttime.auth.dto.result.ReissueResult;
import com.aparttime.auth.service.AuthService;
import com.aparttime.common.response.ApiResponse;
import com.aparttime.security.details.AdminDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")

public class AuthController {

    private final AuthService authService;
    private final AuthCookieManager authCookieManager;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
        @RequestBody LoginRequest request,
        HttpServletResponse response
    ) {
        LoginResult result = authService.login(request);

        authCookieManager.addRefreshToken(
            response,
            result.refreshToken()
        );

        return ResponseEntity.ok(
            ApiResponse.ok(
                LOGIN_SUCCESS,
                result.loginResponse()
            )
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
        @AuthenticationPrincipal AdminDetails admin,
        HttpServletResponse response
    ) {
        authCookieManager.removeRefreshToken(response);

        authService.logout(admin.getAdmin().getId());

        return ResponseEntity.ok(
            ApiResponse.ok(
                LOGOUT_SUCCESS,
                null
            )
        );
    }

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<ReissueResponse>> reissue(
        HttpServletRequest request,
        HttpServletResponse response
    ) {

        String refreshToken = authCookieManager.extractRefreshToken(request);

        ReissueResult result = authService.reissue(refreshToken);

        authCookieManager.addRefreshToken(
            response,
            result.refreshToken()
        );

        return ResponseEntity.ok(
            ApiResponse.ok(
                REISSUE_SUCCESS,
                result.reissueResponse()
            )
        );

    }

}
