package com.spring.sorigalpi.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.spring.sorigalpi.exception.ErrorCode;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
  

	
	@Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

		// 401
        setResponse(response, ErrorCode.INVALID_AUTHORIZATION);
    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().println("{ \"status\" : \"" + errorCode.getHttpStatus()
        		+ "\", \"errorMessage\" : \"" +  "INVALID_AUTHORIZATION"
                + "\", \"code\" : \"" +  errorCode.getCode()
                + "\", \"message\" : \"" + errorCode.getMessage()
                + "\"" +"}"
                );
    }
    
}
