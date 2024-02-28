package com.spring.sorigalpi.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLoginDto {

	private String email;
	private String pwd;
	
	public UsernamePasswordAuthenticationToken toAuthenticationToken() {
		return new UsernamePasswordAuthenticationToken(email,pwd);
	}
	
	@Getter
	@Builder
	public static class TokenResDto{
		private String grantType;
		private String accessToken;
		private String refreshToken;
		private Long refreshTokenExpirationTime;
	}
}
