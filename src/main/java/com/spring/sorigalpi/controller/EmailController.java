package com.spring.sorigalpi.controller;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.sorigalpi.base.BaseResponse;
import com.spring.sorigalpi.base.BaseResponseService;
import com.spring.sorigalpi.base.BaseException;
// import com.spring.sorigalpi.exception.BaseException;
import com.spring.sorigalpi.service.EmailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

/*
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping
@Api(tags = "사용자 인증 확인")

public class EmailController {

	private final EmailService emailService;
	private final BaseResponseService baseResponseService;

	@ApiOperation(
	        value = "사용자 이메일 인증 확인",
	        notes = "사용자가 발급 받은 이메일 인증 토큰을 통해 확인한다.")
    @ApiImplicitParam(
            name = "token",
            value = "사용자 이메일 인증 토큰",
            required = true,
            dataType = "string",
            paramType = "query",
            defaultValue = "None")
            	@ApiResponses({
	        @ApiResponse(code = 200, message = "이메일 인증 성공"),
	        @ApiResponse(code = 401, message = "권한 없음")})
	@GetMapping("/confirmEmail") // 이메일 인증 확인
	public BaseResponse<Object> confirmEmail(@Valid @RequestParam String token) throws BaseException{ // 인증 메일 전송 시 전달된 토큰을 받아온다.
			emailService.verifyEmail(token);
			return baseResponseService.responseSuccess();
		
}
}*/

