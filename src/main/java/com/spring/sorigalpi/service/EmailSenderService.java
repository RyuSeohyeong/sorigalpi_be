package com.spring.sorigalpi.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

	private final JavaMailSender javaMailSender;

	@Async
	public void sendEmail(MimeMessage email) {
		javaMailSender.send(email);
	}

	@Async
	public void sendEmail(String to, String subject, String emailContent) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		mmh.setTo(to); // 수신자 이메일 주소
		mmh.setFrom("email"); // 발신자 이메일 주소
		mmh.setSubject(subject); // 제목
		mmh.setText(emailContent, true); // 내용
		javaMailSender.send(mimeMessage);
	}
}