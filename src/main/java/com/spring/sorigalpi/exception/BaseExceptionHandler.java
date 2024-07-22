package com.spring.sorigalpi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BaseExceptionHandler {

	@ExceptionHandler(OtherException.class)
	protected ResponseEntity<ErrorResponseEntity> handleCustomException(OtherException e) {
		return ErrorResponseEntity.toResponseEntity(e.getErrorCode());
	}

}
