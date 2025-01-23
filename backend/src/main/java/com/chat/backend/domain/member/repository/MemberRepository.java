package com.chat.backend.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.backend.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
