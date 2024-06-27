package com.spring.sorigalpi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "비밀번호 재설정을 위한 이메일")

public class VerifyCodeDto {

	@Schema(description = "이메일", example = "abc@efg.com")
	private String email;
}
