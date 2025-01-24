package com.chat.backend.domain.member.dto;

import java.util.List;

import com.chat.backend.domain.clubmember.entity.ClubMember;
import com.chat.backend.domain.member.entity.Member;

import lombok.Builder;

@Builder
public record MemberDto (Long id, String username, List<ClubMember> members) {

	public static MemberDto from(Member member) {
		return MemberDto.builder()
			.id(member.getId())
			.username(member.getUsername())
			.members(member.getMembers())
			.build();
	}
}
