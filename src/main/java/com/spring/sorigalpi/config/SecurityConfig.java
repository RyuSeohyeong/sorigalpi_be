package com.spring.sorigalpi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.spring.sorigalpi.auth.JwtAuthenticationFilter;
import com.spring.sorigalpi.auth.JwtAuthorizationFilter;
import com.spring.sorigalpi.auth.JwtProvider;
import com.spring.sorigalpi.auth.PrincipalDetailsService;
import com.spring.sorigalpi.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final AuthenticationConfiguration authenticationConfiguration;
	private final PrincipalDetailsService principalDetailsService;
	private final MemberRepository memberRepository;

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public JwtProvider jwtTokenProvider() {
		return new JwtProvider(memberRepository);
	}

	// 회원의 비밀번호 암호화
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 시큐리티 필터 설정
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		 http
	        .cors().disable()
	        .csrf().disable()
	       
	        .sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
	        .formLogin().disable()
	        .httpBasic().disable()
	        .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtTokenProvider()))
	        .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtTokenProvider(),
	                    principalDetailsService))
	        .authorizeRequests()
	        .antMatchers("/member/signUp").permitAll()
	        .antMatchers("/member/login").permitAll()
	        .antMatchers("/member/info/**").permitAll()
	        .antMatchers("/member/find/**").permitAll()
	        .antMatchers("/member/listMembers").hasRole("ADMIN")
	        .antMatchers("/test").hasRole("ADMIN")
	        .antMatchers("/member/jwtTokenInfo").hasRole("ADMIN")
	        .anyRequest().authenticated();
	     

	    return http.build();

	}
}
