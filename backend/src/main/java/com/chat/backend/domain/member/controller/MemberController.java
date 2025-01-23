package com.chat.backend.domain.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.backend.domain.member.dto.MemberDto;
import com.chat.backend.domain.member.dto.MemberForm;
import com.chat.backend.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping
	public ResponseEntity<MemberDto> save(@RequestBody MemberForm memberForm) {
		return ResponseEntity.status(HttpStatus.CREATED).body(memberService.join(memberForm));
	}

	@GetMapping("/{id}")
	public ResponseEntity<MemberDto> getMember(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(memberService.findById(id));
	}
}