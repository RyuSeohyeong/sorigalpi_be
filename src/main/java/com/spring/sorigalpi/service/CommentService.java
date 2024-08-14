package com.spring.sorigalpi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.sorigalpi.base.Base;
import com.spring.sorigalpi.dto.CommentDTO;
import com.spring.sorigalpi.entity.Comment;
import com.spring.sorigalpi.exception.ErrorCode;
import com.spring.sorigalpi.exception.OtherException;
import com.spring.sorigalpi.repository.CommentRepository;

@Service("commentService")
public class CommentService extends Base{
	@Autowired
	private CommentRepository replyRepository;
	
	public CommentDTO createReply(CommentDTO dto) { //댓글 생성
		String uuid =  createShortUuid();
		
		dto.setCommentNo(uuid);
		
		return replyRepository.save(dto.toEntity()).toDTO();
	}
	
	public CommentDTO createReply2(CommentDTO dto) { //답글 생성
		String uuid = createShortUuid();
		
		dto.setCommentNo(uuid);
		
		return replyRepository.save(dto.toEntity()).toDTO();
	}
	
	public String deleteComment(CommentDTO dto, String memberId) { //댓글 삭제
		
		Comment entity = replyRepository.findByReplyNo(dto.getCommentNo())
				.orElseThrow(()-> new OtherException(ErrorCode.COMMENT_NOT_FOUND));
		
		if(entity.getMemberId().equals(memberId)) {
			replyRepository.delete(entity);
			return "삭제 성공";
		}else {
			return "삭제 불가 작성자 정보 다름";
		}
			
	}
	
}
