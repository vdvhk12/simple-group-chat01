package com.chat.backend.domain.member.dto;

import com.chat.backend.domain.member.entity.Member;

import lombok.Builder;

@Builder
public record MemberSimpleDto (Long id, String username) {

	public static MemberSimpleDto from(Member member) {
		return MemberSimpleDto.builder()
			.id(member.getId())
			.username(member.getUsername())
			.build();
	}
}
