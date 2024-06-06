package com.spring.sorigalpi.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	
	//401 Unauthorize 권한이 유효하지 않습니다.
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN_001", "유효하지 않은 토큰입니다."),
	INVALID_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "AUTHORIZE_001", "유효하지 않은 권한입니다."),

	// 403 Forbidden 권한이 없습니다.
	EXPIRED_TOKEN(HttpStatus.FORBIDDEN, "TOKEN_002", "만료된 토큰입니다."),
	NO_AUTHORIZED(HttpStatus.FORBIDDEN, "AUTHORIZE_002", "권한이 없습니다."),

	// 404 Not Found 서버가 요청 받은 리소스를 찾을 수 없음
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_001", "존재하지 않는 이메일입니다."),
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "TOKEN_003", "존재하지 않는 토큰입니다.");
	
	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}