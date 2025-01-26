package com.chat.backend.domain.chatroom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chat.backend.domain.chatroom.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
	@Query("SELECT c FROM ChatRoom c WHERE c.club.id IN " +
		"(SELECT cm.club.id FROM ClubMember cm WHERE cm.member.id = :memberId)")
	List<ChatRoom> findByMemberId(@Param("memberId") Long memberId);
}
