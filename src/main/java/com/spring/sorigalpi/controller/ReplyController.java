package com.spring.sorigalpi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.sorigalpi.auth.PrincipalDetails;
import com.spring.sorigalpi.base.BaseResponse;
import com.spring.sorigalpi.base.BaseResponseService;
import com.spring.sorigalpi.dto.ReplyDTO;
import com.spring.sorigalpi.service.ReplyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@Api(tags = "reply")
@RestController("replyController")
@RequestMapping(value="/reply")
public class ReplyController {
	@Autowired
	private ReplyService replyService;
	@Autowired
	private BaseResponseService baseResponseService;
	
	@ApiOperation(
			value = "댓글 생성 API",
			notes = "책고유Id, 댓글내용 필요") 
	@ApiResponse(code = 200, message = "성공")
	@PostMapping("/createReply") //댓글 생성
	public BaseResponse<Object> createReply(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody ReplyDTO replyDTO) {
		
		replyDTO.setMemberId(principalDetails.getMember().getMemberId());
		ReplyDTO result = replyService.createReply(replyDTO);
		
		return baseResponseService.responseSuccess(result);	
	}
}
