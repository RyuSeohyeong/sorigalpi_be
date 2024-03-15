package com.spring.sorigalpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.sorigalpi.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
    
	Member findByEmail(String email); 
}
