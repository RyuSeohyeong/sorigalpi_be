package com.spring.sorigalpi.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.sorigalpi.auth.PrincipalDetails;
import com.spring.sorigalpi.base.BaseException;
import com.spring.sorigalpi.base.BaseResponse;
import com.spring.sorigalpi.base.BaseResponseService;
import com.spring.sorigalpi.dto.NoticeDto;
import com.spring.sorigalpi.service.NoticeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
	
	@ApiOperation(
	        value = "공지사항 글 조회",
	        notes = "공지사항 글 목록을 조회한다.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "공지사항 목록 조회 성공"),
        @ApiResponse(code = 401, message = "권한 없음")})
	@GetMapping("/listNotices")
	public BaseResponse<Object> listNotices() {
	   
		List<NoticeDto> noticeList = noticeService.listNotice();

	    return baseResponseService.responseSuccess(noticeList);

}
	
	@ApiOperation(
	        value = "공지사항 글 수정",
	        notes = "공지사항 ID를 통해 글을 수정한다.")
    @ApiImplicitParams({
    	@ApiImplicitParam(
                name = "noticeId",
                value = "공지사항 글 ID",
                required = true,
                dataType = "string",
                paramType = "path",
                defaultValue = "None"),
    	@ApiImplicitParam(
                name = "noticeDto",
                value = "공지사항",
                required = true,
                dataType = "string",
                paramType = "body",
                defaultValue = "None")
    })
	@ApiResponses({
        @ApiResponse(code = 200, message = "공지사항 글 수정 성공"),
        @ApiResponse(code = 401, message = "공지사항 글 수정 실패")})
	@PutMapping("/update/{noticeId}")
	public BaseResponse<Object> updateNotice(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable String noticeId, @RequestBody NoticeDto noticeDto) throws BaseException {
		
		noticeDto.setMemberId(principalDetails.getMember().getMemberId());
		
		return baseResponseService.responseSuccess( noticeService.updateNotice(noticeDto, noticeId));
		 
	}
	
	@ApiOperation(
	        value = "공지사항 글 삭제",
	        notes = "사용자의 ID를 통해 탈퇴한다.")
	@ApiImplicitParam(
	        name = "noticeId",
	        value = "공지사항 글 ID",
	        required = true,
	        dataType = "string",
	        paramType = "path",
	        defaultValue = "None")
	@ApiResponses({
        @ApiResponse(code = 200, message = "공지사항 글 작성 성공"),
        @ApiResponse(code = 401, message = "공지사항 글 삭제 실패")})
	@DeleteMapping("/{noticeId}")
	public BaseResponse<Object> deleteNotice(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable String noticeId) throws BaseException {
		 
		NoticeDto noticeDto = new NoticeDto();
		
		noticeDto.setMemberId(principalDetails.getMember().getMemberId());
		
		return baseResponseService.responseSuccess(noticeService.deleteNotice(noticeDto, noticeId));
	}
	
}
