package com.aparttime.member.repository;

import com.aparttime.member.MemberStatus;
import com.aparttime.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    long countByStatus(MemberStatus status);

}
