package com.aparttime.security.details;

import com.aparttime.admin.domain.Admin;
import com.aparttime.admin.repository.AdminRepository;
import com.aparttime.exception.member.MemberNotFoundException;
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

        Long memberId = Long.parseLong(username);

        Admin admin = adminRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);

        return new AdminDetails(admin);
    }

}
