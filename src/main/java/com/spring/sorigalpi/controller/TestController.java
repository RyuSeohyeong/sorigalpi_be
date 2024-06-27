package com.spring.sorigalpi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Test")
 @RestController
public class TestController {

	@ApiOperation(value="테스트", notes="테스트")
	@GetMapping("/test")
	public String test() {
		return "Hello World!";
	}
} 
