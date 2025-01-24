package com.chat.backend.domain.club.entity;

import java.util.ArrayList;
import java.util.List;

import com.chat.backend.domain.chatroom.entity.ChatRoom;
import com.chat.backend.domain.clubmember.entity.ClubMember;
import com.chat.backend.domain.member.entity.Member;
import com.chat.backend.global.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Club extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ClubMember> members;

	@Setter
	@OneToOne(mappedBy = "club", cascade = CascadeType.ALL)
	private ChatRoom chatRoom;

	// 생성 팩토리 메서드
	public static Club of(String name, String description, Member member) {
		// group 객체 생
		Club club = Club.builder()
			.name(name)
			.description(description)
			.member(member)
			.members(new ArrayList<>())
			.build();

		// ChatRoom 생성 후 Group 에 설정
		ChatRoom chatRoom = ChatRoom.of(club);
		club.setChatRoom(chatRoom);

		return club;
	}

	// 연관 관계 편의 메서드 (중간 테이블 연관 관계 설정)
	public void addMember(ClubMember clubMember) {
		members.add(clubMember);
		clubMember.setClub(this);
	}
}
