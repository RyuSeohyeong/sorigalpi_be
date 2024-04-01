package com.spring.sorigalpi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BaseExceptionHandler {
	
	@ExceptionHandler(BaseException.class) // CustomException에서 예외를 잡아서 하나의 메서드에서 공통으로 처리할 수 있게 해준다.
	protected ResponseEntity<ErrorResponseEntity> handleCustomException(BaseException e){
		return ErrorResponseEntity.toResponseEntity(e.getErrorCode());
	}

}
