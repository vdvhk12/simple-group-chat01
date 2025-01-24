package com.chat.backend.domain.club.dto;

import java.util.List;

import com.chat.backend.domain.club.entity.Club;
import com.chat.backend.domain.clubmember.dto.ClubMemberDto;

import lombok.Builder;

@Builder
public record ClubDto (Long id, String name, String description, String creator, List<ClubMemberDto> members) {

	public static ClubDto from(Club club) {
		return ClubDto.builder()
			.id(club.getId())
			.name(club.getName())
			.description(club.getDescription())
			.creator(club.getMember().getUsername())
			.members(club.getMembers().stream().map(ClubMemberDto::from).toList())
			.build();
	}
}
