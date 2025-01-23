package com.chat.backend.domain.member.entity;

import java.util.ArrayList;
import java.util.List;

import com.chat.backend.domain.groupmember.entity.ClubMember;
import com.chat.backend.global.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String password;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ClubMember> members;

	// 생성 팩토리 메서드
	public static Member of(String username, String password) {
		return Member.builder()
			.username(username)
			.password(password)
			.members(new ArrayList<>())
			.build();
	}

	// 연관 관계 편의 메서드 (중간 테이블 연관 관계 설정)
	public void addClubMember(ClubMember clubMember) {
		members.add(clubMember);
		clubMember.setMember(this);
	}
}
