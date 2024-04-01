package com.spring.sorigalpi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class BaseException extends RuntimeException {
	
    private static final long serialVersionUID = 1L;
    
    ErrorCode errorCode;
}