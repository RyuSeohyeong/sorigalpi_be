package com.spring.sorigalpi.auth;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.spring.sorigalpi.dto.TokenDto;
import com.spring.sorigalpi.entity.Member;
import com.spring.sorigalpi.exception.ErrorCode;
import com.spring.sorigalpi.exception.OtherException;
import com.spring.sorigalpi.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtProvider {

	private final MemberRepository memberRepository;
	
	static Long EXPIRE_TIME = 60L * 60L * 1000L * 12; // 토큰 만료 시간: 하루

	@Value("${jwt.secret}")
	private String secretKey;

	private Algorithm getSign() {
		return Algorithm.HMAC512(secretKey);
	}

	// 객체 초기화 후 secretKey를 Base64로 인코딩한다.
	@PostConstruct
	protected void init() {
		this.secretKey = Base64.getEncoder().encodeToString(this.secretKey.getBytes());
	}

    // Jwt Token 생성
    public String generateJwtToken(String memberId, String email) {
        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + 86400000); // 1일

        String accessToken = JWT.create()
                .withSubject(email)
                .withExpiresAt(accessTokenExpiresIn)
                .withClaim("memberId", memberId)
                .withClaim("email", email)
                .sign(this.getSign());

        return accessToken;
    }

    // Refresh Token 생성
    public String generateRefreshToken(String memberId, String email) {
        long now = (new Date()).getTime();
        Date refreshTokenExpiresIn = new Date(now + 2592000000L); // 30일

        String refreshToken = JWT.create()
                .withSubject(email)
                .withExpiresAt(refreshTokenExpiresIn)
                .withClaim("memberId", memberId)
                .withClaim("email", email)
                .sign(this.getSign());

        return refreshToken;
    }
    
    public TokenDto generateJwtTokenDto(String memberId, String email) {
        String accessToken = generateJwtToken(memberId, email);
        String refreshToken = generateRefreshToken(memberId, email);

        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

	// 토큰 검증하기
	// 토큰 만료 시간이 지났는지 확인한다.
		public Member validToken(String accessToken) throws OtherException {
		  
			String email = JWT.require(this.getSign())
		            .build().verify(accessToken).getClaim("email").asString();

		    if (email == null) {
		        throw new OtherException(ErrorCode.INVALID_TOKEN);
		    }

		    Date expiresAt = JWT.require(this.getSign()).acceptExpiresAt(EXPIRE_TIME).build().verify(accessToken)
		            .getExpiresAt();
		    if (!this.validExpiredTime(expiresAt)) {
		        throw new OtherException(ErrorCode.EXPIRED_TOKEN);
		    }

		    return memberRepository.findByEmail(email)
		            .orElseThrow(() -> new OtherException(ErrorCode.MEMBER_NOT_FOUND));
		}

	// 토큰의 만료 시간을 검증한다.
	private boolean validExpiredTime(Date expired) {

		// 만료 시간을 LocalDateTim으로 변경
		LocalDateTime localTimeExpired = expired.toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();

		// 현재 시간이 만료 시간 이전인지를 나타내는 boolean 값을 반환합니다. 이는 토큰의 만료 여부를 판단하는 데 사용됩니다.
		// 현재 시간이 만료시간의 이전이다
		// boolean을 사용하면 그 시간이 특정한 조건을 만족하는지에 대한 여부를 판단할 수 있다.

		return LocalDateTime.now().isBefore(localTimeExpired);

	}
}
