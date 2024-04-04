package com.spring.sorigalpi.auth;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.sorigalpi.entity.Member;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	private final JwtProvider jwtProvider;

	// 인증(Authentication)객체
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {

			ObjectMapper om = new ObjectMapper();
			Member member = om.readValue(request.getInputStream(), Member.class);

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					member.getEmail(), member.getPwd());

			// SpringSecurity의 AuthenticationManager 인터페이스를 통 사용자의 자격 증명을
			// 검증하고, 적절한 Authentication 객체를 반환합니다. (AuthenticationToken엔 사용자의 이메일과 비밀번호가
			// 저장되어있다.
			Authentication authentication = authenticationManager.authenticate(authenticationToken);

			return authentication;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

		String memberId = principalDetails.getMember().getMemberId();
		String email = principalDetails.getMember().getEmail();

		String jwtToken = jwtProvider.generateJwtToken(memberId, email);

		response.addHeader("Authorization", "Bearer " + jwtToken);

	}
}
