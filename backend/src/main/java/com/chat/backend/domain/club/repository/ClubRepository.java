package com.chat.backend.domain.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.backend.domain.club.entity.Club;

public interface ClubRepository extends JpaRepository<Club, Long> {
}
