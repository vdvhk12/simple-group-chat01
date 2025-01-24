package com.chat.backend.domain.clubmember.dto;

import com.chat.backend.domain.clubmember.entity.ClubMember;
import com.chat.backend.domain.clubmember.entity.ClubMemberRole;

import lombok.Builder;

@Builder
public record ClubMemberDto (Long memberId, String username, ClubMemberRole role) {

	public static ClubMemberDto from(ClubMember clubMember) {
		return ClubMemberDto.builder()
			.memberId(clubMember.getMember().getId())
			.username(clubMember.getMember().getUsername())
			.role(clubMember.getClubMemberRole())
			.build();
	}
}
