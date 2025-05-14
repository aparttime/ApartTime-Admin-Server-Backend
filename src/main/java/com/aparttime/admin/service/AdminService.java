package com.aparttime.admin.service;

 import com.aparttime.admin.domain.Admin;
import com.aparttime.admin.dto.request.AdminSignupRequest;
import com.aparttime.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(
        AdminSignupRequest request
    ) {
        Admin admin = new Admin(
            request.username(),
            passwordEncoder.encode(request.password())
        );

        adminRepository.save(admin);
    }

}
