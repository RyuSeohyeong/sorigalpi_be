package com.spring.sorigalpi.base;

import java.util.List;

import org.springframework.stereotype.Component;

import com.spring.sorigalpi.entity.Member;

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
	/*
	// List형 성공 (전달하는 데이터O)
	public <T> ListResponse<Member> getListResponse(List<T> dataList){
		ListResponse listResponse = new ListResponse();
		listResponse.dataList=dataList;
		
	    BaseResponse<List<T>> baseResponse = BaseResponse.<List<T>>builder()
	    		.isSuccess(true)
	            .code(BaseResponseStatus.SUCCESS.getCode())
	            .message(BaseResponseStatus.SUCCESS.getMessage())
	            .data(dataList)
	            .build();

	    listResponse.setSuccess(baseResponse.isSuccess());
	    listResponse.setCode(baseResponse.getCode());
	    listResponse.setMessage(baseResponse.getMessage());
	    listResponse.setDataList(baseResponse.getData());

	        return listResponse;
	}
	
	// 한개의 정보만 조회하기 성공 (전달하는 데이터O)
	public <T> SingleResponse<Member> getSingleResponse(T data){
		SingleResponse singleResponse = new SingleResponse();
		singleResponse.data = data;
		
		BaseResponse<T> baseResponse = BaseResponse.<T>builder()
			.isSuccess(true)
			.code(BaseResponseStatus.SUCCESS.getCode())
        	.message(BaseResponseStatus.SUCCESS.getMessage())
        	.data(data)
        	.build();
		
		singleResponse.setSuccess(baseResponse.isSuccess());
		singleResponse.setCode(baseResponse.getCode());
		singleResponse.setMessage(baseResponse.getMessage());
		singleResponse.setData(baseResponse.getData());
		
		return singleResponse;
	}
	*/
}
