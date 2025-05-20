package com.aparttime.dashboard.service;

import com.aparttime.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final MemberService memberService;

    public long getPendingSignupCount() {
        return memberService.countPendingMembers();
    }

}
