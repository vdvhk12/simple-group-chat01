package com.chat.backend.global.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.backend.domain.member.dto.MemberDto;
import com.chat.backend.domain.member.dto.MemberSimpleDto;
import com.chat.backend.domain.member.entity.Member;
import com.chat.backend.domain.member.repository.MemberRepository;
import com.chat.backend.global.auth.dto.AuthForm;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final MemberRepository memberRepository;

	@PostMapping("/login")
	public ResponseEntity<MemberSimpleDto> login(@RequestBody AuthForm authForm) {
		Member member = memberRepository.findByUsername(authForm.username()).orElse(null);
		assert member != null;
		return ResponseEntity.status(HttpStatus.OK).body(MemberSimpleDto.from(member));
	}
}
