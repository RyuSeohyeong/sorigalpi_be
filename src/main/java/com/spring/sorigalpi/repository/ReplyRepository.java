package com.spring.sorigalpi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.sorigalpi.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, String> {
	public Optional<Reply> findByReplyNo(String replyNo);
}
