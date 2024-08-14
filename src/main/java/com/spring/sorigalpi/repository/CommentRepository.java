package com.spring.sorigalpi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.sorigalpi.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, String> {
	public Optional<Comment> findByReplyNo(String replyNo);
}
