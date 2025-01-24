package com.chat.backend.domain.member.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chat.backend.domain.member.dto.MemberForm;
import com.chat.backend.domain.member.dto.MemberSimpleDto;
import com.chat.backend.domain.member.entity.Member;
import com.chat.backend.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	@Transactional
	public MemberSimpleDto join(MemberForm memberForm) {
		return MemberSimpleDto.from(memberRepository.save(Member.of(memberForm.username(), memberForm.password())));
	}

	@Transactional(readOnly = true)
	public MemberSimpleDto findById(Long id) {
		return MemberSimpleDto.from(Objects.requireNonNull(memberRepository.findById(id).orElse(null)));
	}
}
