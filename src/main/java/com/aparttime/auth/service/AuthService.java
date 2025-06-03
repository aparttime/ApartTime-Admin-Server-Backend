package com.aparttime.auth.service;

import com.aparttime.admin.domain.Admin;
import com.aparttime.auth.dto.result.LoginResult;
import com.aparttime.auth.dto.request.LoginRequest;
import com.aparttime.auth.dto.request.SignupRequest;
import com.aparttime.admin.repository.AdminRepository;
import com.aparttime.auth.dto.response.LoginResponse;
import com.aparttime.config.properties.JwtProperties;
import com.aparttime.exception.auth.InvalidPasswordException;
import com.aparttime.exception.member.MemberNotFoundException;
import com.aparttime.redis.repository.RefreshTokenRepository;
import com.aparttime.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public void signup(
        SignupRequest request
    ) {
        Admin admin = Admin.of(
            request.getUsername(),
            passwordEncoder.encode(request.getPassword())
        );

        adminRepository.save(admin);
    }

    public LoginResult login(
        LoginRequest request
    ) {
        Admin admin = adminRepository.findByUsername(request.username())
            .orElseThrow(MemberNotFoundException::new);

        if (!passwordEncoder.matches(request.password(), admin.getPassword())) {
            throw new InvalidPasswordException();
        }

        String accessToken = jwtTokenProvider.createAccessToken(admin.getUsername());
        String refreshToken = jwtTokenProvider.createRefreshToken(admin.getUsername());

        // save refresh token in Redis
        refreshTokenRepository.save(
            admin.getId(),
            refreshToken,
            jwtProperties.getRefreshTokenExpiration()
        );

        LoginResponse loginResponse = LoginResponse.of(
            admin.getId(),
            admin.getUsername(),
            accessToken
        );

        return LoginResult.of(loginResponse, refreshToken);
    }

    public void logout(
        Long memberId
    ) {
        refreshTokenRepository.delete(memberId);
    }

}
