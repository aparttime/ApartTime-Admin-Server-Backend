package com.aparttime.member.service;

import com.aparttime.member.MemberStatus;
import com.aparttime.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public long countPendingMembers() {
        return memberRepository.countByStatus(MemberStatus.PENDING);
    }

}
