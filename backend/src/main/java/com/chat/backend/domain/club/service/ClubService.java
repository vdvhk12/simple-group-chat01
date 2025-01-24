package com.chat.backend.domain.club.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chat.backend.domain.club.dto.ClubDto;
import com.chat.backend.domain.club.dto.ClubForm;
import com.chat.backend.domain.club.entity.Club;
import com.chat.backend.domain.club.repository.ClubRepository;
import com.chat.backend.domain.clubmember.dto.ClubMemberDto;
import com.chat.backend.domain.clubmember.entity.ClubMember;
import com.chat.backend.domain.clubmember.entity.ClubMemberRole;
import com.chat.backend.domain.clubmember.entity.repository.ClubMemberRepository;
import com.chat.backend.domain.member.entity.Member;
import com.chat.backend.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClubService {

	private final ClubRepository clubRepository;
	private final MemberRepository memberRepository;
	private final ClubMemberRepository clubMemberRepository;

	@Transactional
	public ClubDto create(Long id, ClubForm clubForm) {
		Member member = memberRepository.findById(id).orElse(null);
		Club club = Club.of(clubForm.name(), clubForm.description(), member);


		ClubMember clubMember = ClubMember.of(club, member, ClubMemberRole.MANAGER);
		club.addMember(clubMember);

		return ClubDto.from(clubRepository.save(club));
	}

	@Transactional(readOnly = true)
	public List<ClubDto> getClubs() {
		return clubRepository.findAll().stream().map(ClubDto::from).toList();
	}

	@Transactional(readOnly = true)
	public ClubDto getClub(Long id) {
		return ClubDto.from(Objects.requireNonNull(clubRepository.findById(id).orElse(null)));
	}

	@Transactional
	public ClubMemberDto createApplicationToClub(Long id, Long memberId) {

		Club club = clubRepository.findById(id).orElse(null);

		Member member = memberRepository.findById(memberId).orElse(null);

		ClubMember clubMember = ClubMember.of(club, member, ClubMemberRole.GUEST);

		assert club != null;
		club.addMember(clubMember);

		clubRepository.save(club);

		return ClubMemberDto.from(clubMember);
	}

	@Transactional
	public ClubDto approve(Long id, Long memberId) {
		ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(id, memberId).orElse(null);
		assert clubMember != null;

		clubMember.approve();

		Club club = clubRepository.findById(id).orElse(null);
		assert club != null;

		return ClubDto.from(club);
	}
}
