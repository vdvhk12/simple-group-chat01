package com.chat.backend.domain.message.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.chat.backend.domain.message.entity.Message;

public interface MessageRepository extends MongoRepository<Message, String> {
	Page<Message> findByChatRoomIdOrderByCreatedAt(Long chatRoomId, Pageable pageable);
}
