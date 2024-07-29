package com.spring.sorigalpi.base;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
	
	//------------------성공----------------------------//
	
	SUCCESS(true, 1000, "성공했습니다."),
	
	//------------------------------------------------//

	//------------------실패----------------------------//
	
	//401 Unauthorize 권한이 유효하지 않습니다.
	INVALID_TOKEN(false, 1001, "유효하지 않은 토큰입니다."),
	INVALID_AUTHORIZATION(false, 1002, "유효하지 않은 권한입니다."),
	EMAIL_NOT_VERIFID(false, 1003, "이메일 인증이 필요합니다."),
	INVALID_LINK(false, 1004, "유효하지 않은 링크입니다."),
	MEMBER_NOT_FOUND(false, 1005, "존재하지 않는 이메일입니다."),
	TOKEN_NOT_FOUND(false, 1006, "존재하지 않는 토큰입니다."),
	MEMBER_EXISTED(false, 1007, "이미 존재하는 회원입니다."),
	
	// 403 Forbidden 권한이 없습니다.
	NO_AUTHORIZED(false, 1008, "권한이 없습니다."),
	EXPIRED_TOKEN(false, 1009, "만료된 토큰입니다.");
	
	//------------------------------------------------//
	

	private boolean isSuccess;
	private String message;
	private int code;
	
	BaseResponseStatus(boolean isSuccess, int code, String message){
		this.isSuccess = isSuccess;
		this.code = code;
		this.message = message;
	}
	
	 public int getCode() {
	        return code;
	    }

	    public String getMessage() {
	        return message;
	    }

}
