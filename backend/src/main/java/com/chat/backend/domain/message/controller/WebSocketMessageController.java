package com.chat.backend.domain.message.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.chat.backend.domain.message.dto.MessageForm;
import com.chat.backend.domain.message.entity.Message;
import com.chat.backend.domain.message.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WebSocketMessageController {

	private final MessageRepository messageRepository;

	// 클라이언트가 /app/chat/{id} 경로로 보내는 메시지를 처리
	@MessageMapping("/chat/{chatRoomId}")
	@SendTo("/topic/chatroom/{chatRoomId}")
	public Message sendMessage(@DestinationVariable String chatRoomId, MessageForm messageForm) {
		Message message = Message.of(messageForm.chatRoomId(), messageForm.senderId(), messageForm.senderName(),
			messageForm.content());
		messageRepository.save(message);
		return message;
	}
}
