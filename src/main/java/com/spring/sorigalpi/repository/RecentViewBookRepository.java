package com.spring.sorigalpi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.sorigalpi.entity.RecentViewBook;

public interface RecentViewBookRepository extends JpaRepository<RecentViewBook, String>{
	public RecentViewBook findByBookId(UUID bookId);
}
