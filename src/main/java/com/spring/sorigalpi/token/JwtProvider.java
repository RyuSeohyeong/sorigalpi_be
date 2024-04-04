package com.spring.sorigalpi.token;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.spring.sorigalpi.entity.Member;
import com.spring.sorigalpi.exception.BaseException;
import com.spring.sorigalpi.exception.ErrorCode;
import com.spring.sorigalpi.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtProvider {

	private final MemberRepository memberRepository;

	static Long EXPIRE_TIME = 60L * 60L * 1000L; // 토큰 만료 시간 (1시간)

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

		Date tokenExpiration = new Date(System.currentTimeMillis() + (EXPIRE_TIME));

		String jwtToken = JWT.create().withSubject(email).withExpiresAt(tokenExpiration).withClaim("memberId", memberId)
				.withClaim("email", email).sign(this.getSign());

		return jwtToken;
	}

	// 토큰 검증하기
	// 토큰 만료 시간이 지났는지 확인한다.
	public Member validToken(String jwtToken) {
		try {

			String email = JWT.require(this.getSign()) // JWT 객체를 생성 후 getSign 메소드로 서명에 사용되는 알고리즘을 가져온다.
					.build().verify(jwtToken).getClaim("email").asString(); // JWT 페이로드에서 email 정보를 가져온 후 문자열로 반환한다.

			// email의 값이 비어있는 값이다.
			if (email == null) {
				return null;
			}

			// 토큰의 만료 시간이 지나지 않았는지 확인한다.
			Date expiresAt = JWT.require(this.getSign()).acceptExpiresAt(EXPIRE_TIME).build().verify(jwtToken)
					.getExpiresAt();
			if (!this.validExpiredTime(expiresAt)) {
				// 만료시간이 지나면 만료시간 지남에 대한 예외 반환
				return null;
			}

			// 만료시간이 지나지않았을 때 (토큰이 유효할 때)
			Member member = memberRepository.findByEmail(email)
					.orElseThrow(() -> new BaseException(ErrorCode.MEMBER_NOT_FOUND));
			return member;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

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
