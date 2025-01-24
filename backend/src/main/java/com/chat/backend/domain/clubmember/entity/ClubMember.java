package com.chat.backend.domain.clubmember.entity;

import com.chat.backend.domain.club.entity.Club;
import com.chat.backend.domain.member.entity.Member;
import com.chat.backend.global.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class ClubMember extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Club club;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@Enumerated(EnumType.STRING)
	private ClubMemberRole clubMemberRole;

	// 생성 팩토리 메서드
	public static ClubMember of(Club club, Member member, ClubMemberRole clubMemberRole) {
		return ClubMember.builder()
			.club(club)
			.member(member)
			.clubMemberRole(clubMemberRole)
			.build();
	}

	// 연관 관계 편의 메서드 - Member
	public void setMember(Member member) {
		this.member = member;
		if(!member.getMembers().contains(this)) {
			member.getMembers().add(this);
		}
	}

	// 연관 관계 편의 메서드 - Group
	public void setClub(Club club) {
		this.club = club;
		if(!club.getMembers().contains(this)) {
			club.getMembers().add(this);
		}
	}
}
