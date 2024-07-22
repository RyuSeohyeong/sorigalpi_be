package com.spring.sorigalpi.base;

import org.springframework.stereotype.Component;

@Component
public class BaseResponseServiceImpl implements BaseResponseService {

	
	// 성공 (전달하는 데이터 O)
	public <T> BaseResponse<Object> responseSuccess(T data){
		return BaseResponse.builder()
				.isSuccess(true)
				.code(BaseResponseStatus.SUCCESS.getCode())
				.message(BaseResponseStatus.SUCCESS.getMessage())
				.data(data)
				.build();
	}
	
	// 성공 (전달하는 데이터 X)
	public <T> BaseResponse<Object> responseSuccess(){
		return BaseResponse.builder()
				.isSuccess(true)
				.code(BaseResponseStatus.SUCCESS.getCode())
				.message(BaseResponseStatus.SUCCESS.getMessage())
				.build();
	}
	
	// 실패
	public <T> BaseResponse<Object> responseFail(BaseResponseStatus status){
		return BaseResponse.builder()
				.isSuccess(status.isSuccess())
				.code(status.getCode())
				.message(status.getMessage())
				.build();
	}
}
