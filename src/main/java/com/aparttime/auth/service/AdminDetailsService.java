package com.aparttime.auth.service;

import com.aparttime.admin.domain.Admin;
import com.aparttime.admin.repository.AdminRepository;
import com.aparttime.auth.security.AdminDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(
        String username
    ) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("해당 관리자를 찾을 수 없습니다."));

        return new AdminDetails(admin);
    }

}
