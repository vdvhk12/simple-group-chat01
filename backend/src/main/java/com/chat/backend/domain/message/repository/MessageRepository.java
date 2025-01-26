package com.chat.backend.domain.message.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chat.backend.domain.message.entity.Message;

public interface MessageRepository extends MongoRepository<Message, String> {
	List<Message> findAllByChatRoomIdOrderByCreatedAt(Long chatRoomId);
}
