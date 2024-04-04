package com.spring.sorigalpi.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

	// 403 Forbidden 사용자가 콘텐츠에 접근할 권리를 가지고 있지 않습니다.
	INVALID_TOKEN(HttpStatus.FORBIDDEN, "TOKEN_001", "만료된 토큰입니다."),
	NO_AUTHORIZED(HttpStatus.FORBIDDEN, "AUTHORIZE_001", "인증되지 않았습니다."),

	// 404 Not Found 서버가 요청 받은 리소스를 찾을 수 없음
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_001", "존재하지 않는 이메일입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}