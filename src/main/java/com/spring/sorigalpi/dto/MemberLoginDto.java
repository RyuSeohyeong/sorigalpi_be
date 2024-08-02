package com.spring.sorigalpi.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "사용자 로그인")

public class MemberLoginDto {

	@Schema(description = "이메일", example = "abc@efg.com", required = true)
	private String email;
	
	@Schema(description = "비밀번호", example = "abced123!", required = true)
	private String pwd;
	
}
