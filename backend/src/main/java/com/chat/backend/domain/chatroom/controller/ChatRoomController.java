package com.chat.backend.domain.chatroom.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chat.backend.domain.chatroom.dto.ChatRoomDto;
import com.chat.backend.domain.chatroom.dto.ChatRoomListDto;
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
	public ResponseEntity<List<ChatRoomListDto>> getChatRooms(@PathVariable("id") Long id) {
		List<ChatRoomListDto> chatRooms = chatRoomService.getChatRoomsByMemberId(id);
		return ResponseEntity.status(HttpStatus.OK).body(chatRooms);
	}

	@GetMapping("/{id}/{chatRoomId}")
	public ResponseEntity<ChatRoomDto> getChatRoom(@PathVariable("id") Long id,
		@PathVariable("chatRoomId") Long chatRoomId) {
		ChatRoomDto chatRoomDto = chatRoomService.getChatRoomByMemberIdAndChatRoomId(id, chatRoomId);
		return ResponseEntity.status(HttpStatus.OK).body(chatRoomDto);
	}

	// 채팅방의 메시지 가져오기 (페이징 처리 및 최신순 정렬)
	@GetMapping("/messages/{chatRoomId}")
	public ResponseEntity<Page<Message>> getMessagesByChatRoom(
		@PathVariable Long chatRoomId,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "20") int size) {

		Pageable pageable = PageRequest.of(page, size); // 페이지 번호, 페이지 크기
		Page<Message> messages = messageRepository.findByChatRoomIdOrderByCreatedAtDesc(chatRoomId, pageable);
		return ResponseEntity.ok(messages);
	}
}
