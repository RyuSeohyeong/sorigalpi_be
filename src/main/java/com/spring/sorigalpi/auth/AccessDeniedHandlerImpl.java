package com.spring.sorigalpi.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.spring.sorigalpi.exception.ErrorCode;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

    	// 403
        setResponse(response, ErrorCode.NO_AUTHORIZED);
    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().println("{ \"status\" : \"" + errorCode.getHttpStatus()
        		+ "\", \"errorMessage\" : \"" +  "NO_AUTHORIZED"
                + "\", \"code\" : \"" +  errorCode.getCode()
                + "\", \"message\" : \"" + errorCode.getMessage()
                + "\"" +"}"
                );
    }

}