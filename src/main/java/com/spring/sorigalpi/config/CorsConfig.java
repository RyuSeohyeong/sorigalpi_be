package com.spring.sorigalpi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 설정들을 적용할 범위를 지정한다.
                .allowedOriginPatterns("*") //자원 공유를 허락(요청을 허용)할 Origin, allowedOrigins("*")와 allowCredentials(true) 동시에 사용 불가능
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // 허용할 HTTP method
                .allowedHeaders("*") // 허용할 헤더
                .allowCredentials(false) // 쿠키 인증 요청 허용 (다른 도메인 서버에 인증하는 경우에만 사용, true 설정 시 보안상 이슈가 발생할 수 있다.)
        		.maxAge(3000); // preflight Request에 대한 응답을 브라우저에서 캐싱하는 시간
	}     
}