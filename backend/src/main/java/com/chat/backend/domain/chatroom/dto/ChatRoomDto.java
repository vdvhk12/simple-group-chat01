package com.chat.backend.domain.chatroom.dto;

import com.chat.backend.domain.chatroom.entity.ChatRoom;
import com.chat.backend.domain.club.dto.ClubDto;

import lombok.Builder;

@Builder
public record ChatRoomDto (Long id, ClubDto club){

	public static ChatRoomDto from(ChatRoom chatRoom) {
		return ChatRoomDto.builder()
			.id(chatRoom.getId())
			.club(ClubDto.from(chatRoom.getClub()))
			.build();
	}
}
