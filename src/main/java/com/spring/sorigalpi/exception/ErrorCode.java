package com.spring.sorigalpi.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	
	//401 Unauthorize 권한이 유효하지 않습니다.
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN_001", "유효하지 않은 토큰입니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "TOKEN_002", "존재하지 않는 토큰입니다."),
	INVALID_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "AUTHORIZE_001", "유효하지 않은 권한입니다."),
	EMAIL_NOT_VERIFID(HttpStatus.UNAUTHORIZED, "AUTHORIZE_002", "이메일 인증이 필요합니다."),
	INVALID_LINK(HttpStatus.UNAUTHORIZED, "AUTHORIZE_003", "유효하지 않은 링크입니다."),
	MEMBER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "MEMBER_001", "존재하지 않는 이메일입니다."),
	MEMBER_EXISTED(HttpStatus.UNAUTHORIZED, "MEMBER_002", "이미 존재하는 회원입니다."),
	
	
	// 403 Forbidden 권한이 없습니다.
	EXPIRED_TOKEN(HttpStatus.FORBIDDEN, "TOKEN_003", "만료된 토큰입니다."),
	NO_AUTHORIZED(HttpStatus.FORBIDDEN, "AUTHORIZE_004", "권한이 없습니다.");


	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}