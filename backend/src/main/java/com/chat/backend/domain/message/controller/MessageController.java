package com.chat.backend.domain.message.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.backend.domain.message.dto.MessageForm;
import com.chat.backend.domain.message.entity.Message;
import com.chat.backend.domain.message.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

	private final MessageRepository messageRepository;

	@PostMapping
	public ResponseEntity<?> sendMessage(@RequestBody MessageForm messageForm) {
		Message message = Message.of(messageForm.chatRoomId(), messageForm.senderId(), messageForm.senderName(),
			messageForm.content());
		messageRepository.save(message);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}
}
