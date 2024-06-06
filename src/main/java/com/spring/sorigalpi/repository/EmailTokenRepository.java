package com.spring.sorigalpi.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.sorigalpi.entity.EmailToken;

public interface EmailTokenRepository extends JpaRepository<EmailToken, String> {
	Optional<EmailToken> findByEmailTokenIdAndExpiredDateAfterAndExpired(String emailTokenId, LocalDateTime now, boolean expired);
}