package com.spring.sorigalpi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.spring.sorigalpi.repository.MemberRepository;
import com.spring.sorigalpi.token.JwtProvider;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final MemberRepository memberRepository;
	
	//JwtProvide에 memeberRepositor 의존성 부여
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
    	.httpBasic().disable();
    	
    	http
    	.csrf().ignoringAntMatchers("/**");
    		
    	
    	http
    	.authorizeRequests().antMatchers("/**").permitAll()
		.anyRequest().authenticated();
    	
    	http
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);    	

    	return http.build();

    }
}
