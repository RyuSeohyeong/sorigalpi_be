package com.spring.sorigalpi.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.spring.sorigalpi.entity.Member;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private JwtProvider jwtProvider;

	private PrincipalDetailsService principalDetailsService;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider,
			PrincipalDetailsService principalDetailsService) {
		super(authenticationManager);
		this.jwtProvider = jwtProvider;
		this.principalDetailsService = principalDetailsService;
	}

	// 인증이나 권한이 필요할 때 통과하는 필터
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String jwtHeader = request.getHeader("Authorization");

		// header가 있는지 확인
		if (jwtHeader == null || !jwtHeader.startsWith("Bearer ")) {
			chain.doFilter(request, response);

			return;
		}

		// JWT 토큰을 검증(사용자 정보가 있는 jwtToken이 유효한지 검증)
		String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
		Member tokenMember = jwtProvider.validToken(jwtToken);
		if (tokenMember != null) {
			PrincipalDetails principalDetails = new PrincipalDetails(tokenMember);

			// UsernamePasswordAuthenticationToken을 사용해 사용자의 인증 정보를 생성한 후
			// principalDetails 객체에서 사용자의 식별 정보와 권한을 가져와서 값에 저장한다.
			Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null,
					principalDetails.getAuthorities());

			// SecurityContextHolder.getContext(): 보안 컨텍스트(사용자의 인증 정보,권한 등을 저장한다.)
			// authentication에 사용자 정보가 저장되어있데 보안 컨텍스트에 인증된 사용자 정보를 저장한다.
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
		    // 유효한 토큰 null인 경우에 대한 처리
		    System.out.println("tokenMember is null. Unable to authenticate.");
		}
		chain.doFilter(request, response); //필터 단에서 null일 경우 유효한 토큰이 아님이라고 예외 처리
} 
}