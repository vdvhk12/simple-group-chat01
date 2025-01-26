package com.chat.backend.domain.chatroom.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chat.backend.domain.chatroom.dto.ChatRoomDto;
import com.chat.backend.domain.chatroom.dto.ChatRoomListDto;
import com.chat.backend.domain.chatroom.repository.ChatRoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

	private final ChatRoomRepository chatRoomRepository;

	@Transactional(readOnly = true)
	public List<ChatRoomListDto> getChatRoomsByMemberId(Long memberId) {
		return chatRoomRepository.findByMemberId(memberId).stream().map(ChatRoomListDto::from).toList();
	}

	@Transactional(readOnly = true)
	public ChatRoomDto getChatRoomByMemberIdAndChatRoomId(Long id, Long memberId) {
		return ChatRoomDto.from(
			Objects.requireNonNull(chatRoomRepository.findByMemberIdAndChatRoomId(id, memberId).orElse(null)));
	}
}
