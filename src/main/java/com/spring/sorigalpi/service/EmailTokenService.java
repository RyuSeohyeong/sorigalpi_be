package com.spring.sorigalpi.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

import com.spring.sorigalpi.entity.EmailToken;
import com.spring.sorigalpi.exception.BaseException;
import com.spring.sorigalpi.exception.ErrorCode;
import com.spring.sorigalpi.repository.EmailTokenRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailTokenService {

	private final EmailTokenRepository emailTokenRepository;
    private final EmailSenderService emailSenderService;

	public String createEmailToken(String memberId, String receiver) throws MessagingException { // 이메일 인증 토큰을 생성한다.

		EmailToken emailToken = EmailToken.createEmailToken(memberId);
		emailTokenRepository.save(emailToken); // 이메일 토큰을 저장한다.
		
		String emailContent = "<div>"
	            + "<h1>소리갈피에 가입하신 것을 환영합니다!</h1>"
	            + "<br>"
	            + "<p>아래에 있는 링크를 클릭하면 이메일 인증이 완료됩니다.</p>"
	            + "<a href='http://localhost:8080/confirmEmail?token=" + emailToken.getEmailTokenId() + "'> 인증 링크 </a>"
	            + "</div>";

		emailSenderService.sendEmail(receiver, "소리갈피 회원 인증 메일입니다.", emailContent); // 이메일 전송한다.

		return emailToken.getEmailTokenId();    // 인증 메일 전송 시 토큰 반환한다.
	}

	public EmailToken findByEmailTokenIdIdAndExpiredDateAfterAndExpired(String emailTokenId) throws BaseException { // 유효한 이메일 인증 토큰을 가져온다.
		Optional<EmailToken> emailToken = emailTokenRepository.findByEmailTokenIdAndExpiredDateAfterAndExpired(emailTokenId, LocalDateTime.now(), false);

		return emailToken.orElseThrow(() -> new BaseException(ErrorCode.TOKEN_NOT_FOUND));
	}
}