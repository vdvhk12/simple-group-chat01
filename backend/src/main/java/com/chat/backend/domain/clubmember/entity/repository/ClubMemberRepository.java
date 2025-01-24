package com.chat.backend.domain.clubmember.entity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.backend.domain.clubmember.entity.ClubMember;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {

	Optional<ClubMember> findByClubIdAndMemberId(Long clubId, Long memberId);
}
