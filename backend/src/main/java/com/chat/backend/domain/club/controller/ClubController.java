package com.chat.backend.domain.club.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.backend.domain.club.dto.ClubDto;
import com.chat.backend.domain.club.dto.ClubForm;
import com.chat.backend.domain.club.service.ClubService;
import com.chat.backend.domain.clubmember.dto.ClubMemberDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clubs")
public class ClubController {

	private final ClubService clubService;

	@PostMapping("/{id}")
	public ResponseEntity<ClubDto> create(@PathVariable("id") Long id, @RequestBody ClubForm clubForm) {

		return ResponseEntity.status(HttpStatus.CREATED).body(clubService.create(id, clubForm));
	}

	@GetMapping
	public ResponseEntity<List<ClubDto>> getClubs() {
		return ResponseEntity.status(HttpStatus.OK).body(clubService.getClubs());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClubDto> getClub(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(clubService.getClub(id));
	}

	@PostMapping("/{id}/application/{memberId}")
	public ResponseEntity<ClubMemberDto> createApplication(@PathVariable("id") Long id,
		@PathVariable("memberId") Long memberId) {
		ClubMemberDto application = clubService.createApplicationToClub(id, memberId);

		return ResponseEntity.status(HttpStatus.CREATED).body(application);
	}

	@GetMapping("/{id}/approve/{memberId}")
	public ResponseEntity<ClubDto> approve(@PathVariable("id") Long id, @PathVariable("memberId") Long memberId) {
		ClubDto approve = clubService.approve(id, memberId);
		return ResponseEntity.status(HttpStatus.OK).body(approve);
	}
}
