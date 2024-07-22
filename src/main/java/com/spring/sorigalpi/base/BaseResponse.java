package com.spring.sorigalpi.base;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BaseResponse<T> {
	
	private boolean isSuccess;
	
	private int code;
	
	private String message;
	
	private T data;

@Builder
public BaseResponse(boolean isSuccess, int code, String message, T data) {
	this.isSuccess = isSuccess;
	this.code = code;
	this.message = message;
	this.data = data;
}
}