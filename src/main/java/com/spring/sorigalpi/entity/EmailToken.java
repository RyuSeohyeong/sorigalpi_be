package com.spring.sorigalpi.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "t_emailtoken")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailToken {

	private static final long EMAIL_TOKEN_EXPIRATION_TIME = 5L; // 이메일 토큰 만료 시간: 5분

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "emailTokenId")
	private String emailTokenId;

	@Column(name = "memberId")
	private String memberId;

	@Column(name = "expired")
	private boolean expired;

	@Column(name = "expiredDate")
	private LocalDateTime expiredDate;

	public static EmailToken createEmailToken(String memberId) {
		
		EmailToken emailToken = new EmailToken();
		emailToken.memberId = memberId;
		emailToken.expired = false;
		emailToken.expiredDate = LocalDateTime.now().plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME);

		return emailToken;
	}
	public void usedEmailToken() { // 토큰 사용으로 인한 만료
		this.expired = true;

	}
}