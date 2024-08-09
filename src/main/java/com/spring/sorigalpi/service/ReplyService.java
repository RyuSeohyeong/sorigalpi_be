package com.spring.sorigalpi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.sorigalpi.base.Base;
import com.spring.sorigalpi.dto.ReplyDTO;
import com.spring.sorigalpi.entity.Reply;
import com.spring.sorigalpi.exception.ErrorCode;
import com.spring.sorigalpi.exception.OtherException;
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
	
	public String deleteComment(ReplyDTO dto, String memberId) { //댓글 삭제
		
		Reply entity = replyRepository.findByReplyNo(dto.getReplyNo())
				.orElseThrow(()-> new OtherException(ErrorCode.COMMENT_NOT_FOUND));
		
		if(entity.getMemberId().equals(memberId)) {
			replyRepository.delete(entity);
			return "삭제 성공";
		}else {
			return "삭제 불가 작성자 정보 다름";
		}
			
	}
	
}
