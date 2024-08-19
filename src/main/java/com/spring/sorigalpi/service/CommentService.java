package com.spring.sorigalpi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.sorigalpi.base.Base;
import com.spring.sorigalpi.dto.CommentDTO;
import com.spring.sorigalpi.entity.Book;
import com.spring.sorigalpi.entity.Comment;
import com.spring.sorigalpi.exception.ErrorCode;
import com.spring.sorigalpi.exception.OtherException;
import com.spring.sorigalpi.repository.CommentRepository;

@Service("commentService")
public class CommentService extends Base{
	@Autowired
	private CommentRepository commentRepository;
	
	public CommentDTO createComment(CommentDTO dto) { //댓글 생성
		String uuid =  createShortUuid();
		
		dto.setCommentNo(uuid);
		
		return commentRepository.save(dto.toEntity()).toDTO();
	}
	
	public CommentDTO createReply(CommentDTO dto) { //답글 생성
		String uuid = createShortUuid();
		
		dto.setCommentNo(uuid);
		
		return commentRepository.save(dto.toEntity()).toDTO();
	}
	
	public String deleteComment(CommentDTO dto, String memberId) { //댓글 삭제
		
		Comment entity = commentRepository.findByReplyNo(dto.getCommentNo())
				.orElseThrow(()-> new OtherException(ErrorCode.COMMENT_NOT_FOUND));
		
		if(entity.getMemberId().equals(memberId)) {
			commentRepository.delete(entity);
			return "삭제 성공";
		}else {
			return "삭제 불가 작성자 정보 다름";
		}
			
	}
	@Transactional
	public String updateComment(CommentDTO dto, String memberId) { //댓글 수정
		
		Comment entity = commentRepository.findByReplyNo(dto.getCommentNo())
				.orElseThrow(()-> new OtherException(ErrorCode.COMMENT_NOT_FOUND));
		
		String checkMemberId = entity.getMemberId();
		
		if(checkMemberId.equals(memberId)) {
			entity.updateCommentContent(dto.getContent());
			return "update success";
		}else {
			return "update fail (User information is different)";
		}
		
	}
	
	public List<CommentDTO> getAllComment(){ //댓글 목록
		
		List<Comment> commentList = commentRepository.findAll();
		
		List<CommentDTO> dtoList = commentList.stream().map(Comment::toDTO).collect(Collectors.toList());
		
		return dtoList;
	}
	
}
