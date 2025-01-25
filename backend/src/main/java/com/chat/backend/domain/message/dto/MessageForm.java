package com.chat.backend.domain.message.dto;

public record MessageForm (Long chatRoomId, Long senderId, String senderName, String content) {
}
