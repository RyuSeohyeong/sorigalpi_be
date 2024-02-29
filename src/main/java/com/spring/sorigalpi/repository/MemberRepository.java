package com.spring.sorigalpi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.sorigalpi.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
    
	Optional<Member> findByEmail(String email); 
}
