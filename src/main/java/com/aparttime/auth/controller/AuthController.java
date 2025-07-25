package com.aparttime.auth.controller;

import static com.aparttime.common.response.ResponseMessage.*;

import com.aparttime.auth.cookie.AuthCookieManager;
import com.aparttime.auth.dto.request.SignupRequest;
import com.aparttime.auth.dto.response.SecondaryTokenResponse;
import com.aparttime.auth.dto.response.SignupResponse;
import com.aparttime.auth.dto.result.LoginResult;
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

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponse>> signup(
        @RequestBody SignupRequest request
    ) {
        SignupResponse signupResponse = authService.signup(request);

        return ResponseEntity.ok(
            ApiResponse.ok(
                SIGNUP_SUCCESS,
                signupResponse
            )
        );
    }

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
        HttpServletRequest request,
        HttpServletResponse response
    ) {

        String refreshToken = authCookieManager.extractRefreshToken(request);

        authService.logout(admin.getMemberId(), refreshToken);

        authCookieManager.removeRefreshToken(response);

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

    @PostMapping("/st")
    public ResponseEntity<ApiResponse<SecondaryTokenResponse>> issueSecondaryToken(
        @AuthenticationPrincipal AdminDetails admin
    ) {
        SecondaryTokenResponse response = authService.issueSecondaryToken(admin.getMemberId());

        return ResponseEntity.ok(
            ApiResponse.ok(
                SECONDARY_TOKEN_ISSUE_SUCCESS,
                response)
        );
    }

}
