package com.spring.sorigalpi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BaseExceptionHandler {

	@ExceptionHandler(BaseException.class)
	protected ResponseEntity<ErrorResponseEntity> handleCustomException(BaseException e) {
		return ErrorResponseEntity.toResponseEntity(e.getErrorCode());
	}

}
