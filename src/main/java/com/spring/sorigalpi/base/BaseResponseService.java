package com.spring.sorigalpi.base;

public interface BaseResponseService {
	
	// 성공 (전달하는 데이터 O)
	<T> BaseResponse<Object> responseSuccess(T data);
	
	// 성공 (전달하는 데이터 X)

	<T> BaseResponse<Object> responseSuccess();
	
	// 실패
	<T> BaseResponse<Object> responseFail(BaseResponseStatus status);

}
