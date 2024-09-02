package com.spring.sorigalpi.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.sorigalpi.auth.PrincipalDetails;
import com.spring.sorigalpi.base.BaseResponse;
import com.spring.sorigalpi.base.BaseResponseService;
import com.spring.sorigalpi.dto.NoticeDto;
import com.spring.sorigalpi.service.NoticeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController("noticeController")
@RequestMapping(value="/notice")
@Api(tags = "공지사항")
public class NoticeController {
	
	private final NoticeService noticeService;

	private final BaseResponseService baseResponseService;
	
	@ApiOperation(
	        value = "공지사항 글 작성",
	        notes = "관리자가 공지사항 글을 작성한다.")
    @ApiImplicitParam(
            name = "NoticeDto",
            value = "공지사항 글",
            required = true,
            dataType = "string",
            paramType = "body",
            defaultValue = "None")
	@ApiResponses({
	        @ApiResponse(code = 200, message = "공지사항 글 작성 성공"),
	        @ApiResponse(code = 401, message = "권한 없음")})
	@PostMapping("/write")
	public BaseResponse<Object> createNotice(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody NoticeDto noticeDto){
		
		noticeDto.setMemberId(principalDetails.getMember().getMemberId());
		
		noticeService.createNotice(noticeDto);
		
		return baseResponseService.responseSuccess(noticeDto);
	}

}
