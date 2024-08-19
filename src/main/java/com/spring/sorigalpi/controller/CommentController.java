package com.spring.sorigalpi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	private CommentService commentService;
	@Autowired
	private BaseResponseService baseResponseService;
	
	@ApiOperation(
			value = "댓글 생성 API",
			notes = "책고유Id, 댓글내용 필요") 
	@ApiResponse(code = 1000, message = "성공")
	@PostMapping("/createComment") //댓글 생성
	public BaseResponse<Object> createComment(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody CommentDTO dto) {
		
		dto.setMemberId(principalDetails.getMember().getMemberId());
		CommentDTO result = commentService.createComment(dto);
		
		return baseResponseService.responseSuccess(result);	
	}
	
	@ApiOperation(
			value = "답글 생성 API",
			notes = "부모 댓글 식별번호 ,책고유Id, 댓글내용 필요") 
	@ApiResponse(code = 1000, message = "성공")
	@PostMapping("/createReply") //답글 생성
	public BaseResponse<Object> createReply(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody CommentDTO dto) {
		
		dto.setMemberId(principalDetails.getMember().getMemberId());
		CommentDTO result = commentService.createReply(dto);
		
		return baseResponseService.responseSuccess(result);	
	}
	
	@ApiOperation(
			value = "댓글 삭제 API",
			notes = "댓글 식별번호 필요") 
	@ApiResponse(code = 1000, message = "성공")
	@DeleteMapping("/deleteComment") //댓글 삭제
	public BaseResponse<Object> deleteComment(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody CommentDTO dto) {
		
		String memberId = principalDetails.getMember().getMemberId();
		String result = commentService.deleteComment(dto, memberId);
		
		return baseResponseService.responseSuccess(result);	
	}
	
	@ApiOperation(
			value = "댓글 수정 API",
			notes = "댓글 식별번호, 내용") 
	@ApiResponse(code = 1000, message = "성공")
	@PutMapping("/updateComment") //댓글 수정
	public BaseResponse<Object> updateComment(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody CommentDTO dto) {
		
		String memberId = principalDetails.getMember().getMemberId();
		String result = commentService.updateComment(dto, memberId);
		
		return baseResponseService.responseSuccess(result);	
	}
	
	@ApiOperation(
			value = "댓글 불러오기 API",
			notes = "모든 댓글 목록") 
	@ApiResponse(code = 1000, message = "성공")
	@PostMapping("/AllComment") 
	public BaseResponse<Object> allComment() {
		
		List<CommentDTO> dtoList = commentService.getAllComment();
		
		return baseResponseService.responseSuccess(dtoList);	
	}
	
	@ApiOperation(
			value = "답글 불러오기 API",
			notes = "답글 불러오기") 
	@ApiResponse(code = 1000, message = "성공")
	@PostMapping("/getReply") 
	public BaseResponse<Object> getReply(@RequestBody CommentDTO dto) {
		
		List<CommentDTO> dtoList = commentService.getReply(dto);
		
		if (dtoList.size()!=0) {
			return baseResponseService.responseSuccess(dtoList);
		}else {
			return baseResponseService.responseSuccess("No reply");
		}
			
	}
	
	
}
