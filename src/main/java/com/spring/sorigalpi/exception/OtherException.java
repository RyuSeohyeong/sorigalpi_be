package com.spring.sorigalpi.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class OtherException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	ErrorCode errorCode;

	public OtherException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public HttpStatus getHttpStatus() {
		return errorCode.getHttpStatus();
	}

	public String getErrorMessage() {
		return errorCode.getMessage();
	}

}