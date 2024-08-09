package com.spring.sorigalpi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.sorigalpi.base.Base;
import com.spring.sorigalpi.dto.ReplyDTO;
import com.spring.sorigalpi.entity.Reply;
import com.spring.sorigalpi.repository.ReplyRepository;

@Service("replyService")
public class ReplyService extends Base{
	@Autowired
	private ReplyRepository replyRepository;
	
	public ReplyDTO createReply(ReplyDTO dto) { //댓글 생성
		String uuid =  createShortUuid();
		
		dto.setReplyNo(uuid);
		
		return replyRepository.save(dto.toEntity()).toDTO();
	}
	
	public ReplyDTO createReply2(ReplyDTO dto) { //답글 생성
		String uuid = createShortUuid();
		
		dto.setReplyNo(uuid);
		
		return replyRepository.save(dto.toEntity()).toDTO();
	}
	
}
