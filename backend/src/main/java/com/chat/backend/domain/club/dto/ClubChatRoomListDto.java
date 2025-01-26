package com.chat.backend.domain.club.dto;

import com.chat.backend.domain.club.entity.Club;

import lombok.Builder;

@Builder
public record ClubChatRoomListDto(Long id, String name) {

	public static ClubChatRoomListDto from(Club club) {
		return ClubChatRoomListDto.builder()
			.id(club.getId())
			.name(club.getName())
			.build();
	}
}
