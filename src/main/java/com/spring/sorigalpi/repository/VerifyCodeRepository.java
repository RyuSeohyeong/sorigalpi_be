package com.spring.sorigalpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.sorigalpi.entity.VerifyCode;

public interface VerifyCodeRepository extends JpaRepository<VerifyCode, Long> {
	
	public VerifyCode findByEmail(String email);

	public VerifyCode findByCode(String code);

	public boolean existsByEmail(String email);

	public void deleteByEmail(String email);
}