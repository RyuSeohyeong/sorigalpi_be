package com.spring.sorigalpi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.sorigalpi.auth.PrincipalDetails;
import com.spring.sorigalpi.base.BaseResponse;
import com.spring.sorigalpi.base.BaseResponseService;
import com.spring.sorigalpi.dto.CommentDTO;
import com.spring.sorigalpi.service.CommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@Api(tags = "comment")
@RestController("commentController")
@RequestMapping(value="/reply")
public class CommentController {
	@Autowired
	private CommentService replyService;
	@Autowired
	private BaseResponseService baseResponseService;
	
	@ApiOperation(
			value = "댓글 생성 API",
			notes = "책고유Id, 댓글내용 필요") 
	@ApiResponse(code = 1000, message = "성공")
	@PostMapping("/createReply") //댓글 생성
	public BaseResponse<Object> createReply(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody CommentDTO replyDTO) {
		
		replyDTO.setMemberId(principalDetails.getMember().getMemberId());
		CommentDTO result = replyService.createReply(replyDTO);
		
		return baseResponseService.responseSuccess(result);	
	}
	
	@ApiOperation(
			value = "답글 생성 API",
			notes = "부모 댓글 식별번호 ,책고유Id, 댓글내용 필요") 
	@ApiResponse(code = 1000, message = "성공")
	@PostMapping("/createReply2") //답글 생성
	public BaseResponse<Object> createReply2(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody CommentDTO replyDTO) {
		
		replyDTO.setMemberId(principalDetails.getMember().getMemberId());
		CommentDTO result = replyService.createReply2(replyDTO);
		
		return baseResponseService.responseSuccess(result);	
	}
	
	@ApiOperation(
			value = "댓글 삭제 API",
			notes = "댓글 식별번호 필요") 
	@ApiResponse(code = 1000, message = "성공")
	@DeleteMapping("/deleteComment") //댓글 삭제
	public BaseResponse<Object> deleteComment(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody CommentDTO replyDTO) {
		
		String memberId = principalDetails.getMember().getMemberId();
		String result = replyService.deleteComment(replyDTO, memberId);
		
		return baseResponseService.responseSuccess(result);	
	}
}
