package com.spring.sorigalpi.base;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BaseResponse<T> {
	private T data;
	private String message;
	private int status;

	public BaseResponse(T data) {
		this.data = data;
		this.message = "Success";
		this.status = HttpStatus.OK.value();
	}

	public BaseResponse(String message, int status) {
		this.message = message;
		this.status = status;
	}
}