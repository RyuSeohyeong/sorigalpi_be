package com.spring.sorigalpi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "비밀번호 확인")

public class VerifyCodeDto {

	@ApiModelProperty(value = "이메일", required = true)
	private String email;
}
