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

import lombok.RequiredArgsConstructor;

/*
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping
public class EmailController {

	private final EmailService emailService;
	private final BaseResponseService baseResponseService;

	@GetMapping("/confirmEmail") // 이메일 인증 확인
	public BaseResponse<Object> confirmEmail(@Valid @RequestParam String token) { // 인증 메일 전송 시 전달된 토큰을 받아온다.
		try {
			boolean result = emailService.verifyEmail(token);
			return new BaseResponse<>(result);
		} catch (SecondException exception) {
			return new baseResponseService.responseFail(exception.status);
		}
	}
}

*/