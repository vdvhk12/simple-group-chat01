package com.chat.backend.domain.chatroom.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.backend.domain.chatroom.dto.ChatRoomDto;
import com.chat.backend.domain.chatroom.service.ChatRoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/chatrooms")
@RequiredArgsConstructor
public class ChatRoomController {

	private final ChatRoomService chatRoomService;

	@GetMapping("/{id}")
	public ResponseEntity<List<ChatRoomDto>> getChatRooms(@PathVariable("id") Long id) {
		List<ChatRoomDto> chatRooms = chatRoomService.getChatRoomsByMemberId(id);
		return ResponseEntity.status(HttpStatus.OK).body(chatRooms);
	}

	@GetMapping("/{id}/{chatRoomId}")
	public ResponseEntity<ChatRoomDto> getChatRoom(@PathVariable("id") Long id,
		@PathVariable("chatRoomId") Long chatRoomId) {
		ChatRoomDto chatRoomDto = chatRoomService.getChatRoomByMemberIdAndChatRoomId(id, chatRoomId);
		return ResponseEntity.status(HttpStatus.OK).body(chatRoomDto);
	}
}
