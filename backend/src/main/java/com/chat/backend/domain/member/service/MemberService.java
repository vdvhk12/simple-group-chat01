package com.chat.backend.domain.member.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chat.backend.domain.member.dto.MemberDto;
import com.chat.backend.domain.member.dto.MemberForm;
import com.chat.backend.domain.member.entity.Member;
import com.chat.backend.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	@Transactional
	public MemberDto join(MemberForm memberForm) {
		return MemberDto.from(memberRepository.save(Member.of(memberForm.username(), memberForm.password())));
	}

	@Transactional(readOnly = true)
	public MemberDto findById(Long id) {
		return MemberDto.from(Objects.requireNonNull(memberRepository.findById(id).orElse(null)));
	}
}
