package com.chat.backend.domain.chatroom.dto;

import com.chat.backend.domain.chatroom.entity.ChatRoom;
import com.chat.backend.domain.club.dto.ClubChatRoomListDto;

import lombok.Builder;

@Builder
public record ChatRoomListDto (Long id, ClubChatRoomListDto club){

	public static ChatRoomListDto from(ChatRoom chatRoom) {
		return ChatRoomListDto.builder()
			.id(chatRoom.getId())
			.club(ClubChatRoomListDto.from(chatRoom.getClub()))
			.build();
	}
}
