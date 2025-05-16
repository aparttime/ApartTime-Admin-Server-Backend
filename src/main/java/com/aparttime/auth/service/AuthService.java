package com.aparttime.auth.service;

import com.aparttime.admin.domain.Admin;
import com.aparttime.auth.dto.request.SignupRequest;
import com.aparttime.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(
        SignupRequest request
    ) {
        Admin admin = Admin.of(
            request.getUsername(),
            passwordEncoder.encode(request.getPassword())
        );

        adminRepository.save(admin);
    }

}
