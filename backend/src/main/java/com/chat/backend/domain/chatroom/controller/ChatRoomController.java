package com.chat.backend.domain.chatroom.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.backend.domain.chatroom.dto.ChatRoomDto;
import com.chat.backend.domain.chatroom.service.ChatRoomService;
import com.chat.backend.domain.message.entity.Message;
import com.chat.backend.domain.message.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/chatrooms")
@RequiredArgsConstructor
public class ChatRoomController {

	private final ChatRoomService chatRoomService;
	private final MessageRepository messageRepository;

	@GetMapping("/{id}")
	public ResponseEntity<ChatRoomDto> getChatRoom(@PathVariable Long id) {
		ChatRoomDto chatRoomDto = chatRoomService.getChatRoomByChatRoomId(id);
		return ResponseEntity.status(HttpStatus.OK).body(chatRoomDto);
	}

	// 채팅방의 메시지 가져오기 (페이징 처리 및 최신순 정렬)
	@GetMapping("/{id}/messages")
	public ResponseEntity<List<Message>> getMessagesByChatRoom(@PathVariable Long id) {
		List<Message> messages = messageRepository.findAllByChatRoomIdOrderByCreatedAt(id);
		return ResponseEntity.ok(messages);
	}
}
