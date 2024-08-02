package com.spring.sorigalpi.base;

import java.util.List;

import com.spring.sorigalpi.entity.Member;

public interface BaseResponseService {
	
	// 성공 (전달하는 데이터 O)
	<T> BaseResponse<Object> responseSuccess(T data);
	
	// 성공 (전달하는 데이터 X)
	<T> BaseResponse<Object> responseSuccess();
	
	// 실패
	<T> BaseResponse<Object> responseFail(BaseResponseStatus status);
	
	// List형 성공 (전달하는 데이터O)
	<T> ListResponse<Member> getListResponse(List<T> dataList);

	// 한개의 정보만 조회하기 성공 (전달하는 데이터O)
	<T> SingleResponse<Member> getSingleResponse(T data);
	
}
