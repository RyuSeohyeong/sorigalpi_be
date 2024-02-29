package com.spring.sorigalpi.dto;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Builder;

@Builder
public class MemberLoginDto {

	private String email;
	private String pwd;
	
	UsernamePasswordAuthenticationToken toAuthenticattionToken() {
		return new UsernamePasswordAuthenticationToken(email, pwd);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	}
	
