package com.spring.sorigalpi.base;

import lombok.Getter;

@Getter
public class SingleResponse<T> extends BaseResponse {
	
	T data;

}
