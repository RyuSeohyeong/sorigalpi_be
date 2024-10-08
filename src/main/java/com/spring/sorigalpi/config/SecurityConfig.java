package com.spring.sorigalpi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

import com.spring.sorigalpi.auth.AccessDeniedHandlerImpl;
import com.spring.sorigalpi.auth.AuthenticationEntryPointImpl;
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
	private final AuthenticationEntryPointImpl authenticationEntryPointImpl;
	private final AccessDeniedHandlerImpl accessDeniedHandlerImpl;
	

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
		 	.csrf().disable() // Cross Site Request Forgery 사이트간 요청 위조 disable 처리
	        .cors() //Cross Origin Resource Sharing 다른 출처 (Protocol + Host) 자원 공유 able 처리 (서버의 위치를 찾아가기 위해)
	        .and()
	        .sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
	        .formLogin().disable()
	        .httpBasic().disable()
	        .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtTokenProvider())) // 인증
	        .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtTokenProvider(), // 인가
	                    principalDetailsService))
	        .authorizeRequests()
	      
            .antMatchers(publicAuth()).permitAll()
            .antMatchers(adminAuth()).hasRole("ADMIN")
	        
	        .anyRequest().authenticated()
	        .and()
	        .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPointImpl)
            .accessDeniedHandler(accessDeniedHandlerImpl);
           

	    return http.build();

	   
	}
	
	 private String[] publicAuth() {
	        return new String[]{
	            "/swagger", 
	            "/swagger-ui.html", 
	            "/swagger-ui/**", 
	            "/api-docs", 
	            "/api-docs/**", 
	            "/v3/api-docs/**", 
	            "/member/signUp", 
	            "/member/login",
	            "/member/jwtTokenInfo",
	            "/member/info/**",
	            "/member/find/pwd/**",
	            "/confirmEmail/**",
	            "/recentViewBook/**",
	            "/book/**",
	            "/notice/listNotices"
	        };
	    }

	    private String[] adminAuth() {
	        return new String[]{
	            "/member/listMembers",
	            "/member/find/email/**",
	            "/notice/update/**",
	            "/notice/delete/**"
	        };
	    }
	
	
	 @Bean public HttpFirewall defaultHttpFirewall() {
	        return new DefaultHttpFirewall();
}
}
