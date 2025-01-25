package com.chat.backend.domain.message.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "messages")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Message {

	@Id
	private String id;

	private Long chatRoomId;

	private Long senderId;

	private String senderName;

	private String content;

	@CreatedDate
	@Field("createdAt")
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Field("modifiedAt")
	private LocalDateTime modifiedAt;

	public static Message of(Long chatRoomId, Long senderId, String senderName, String content) {
		return Message.builder()
			.chatRoomId(chatRoomId)
			.senderId(senderId)
			.senderName(senderName)
			.content(content)
			.build();
	}
}
