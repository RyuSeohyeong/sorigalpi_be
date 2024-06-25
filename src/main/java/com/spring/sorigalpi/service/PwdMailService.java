package com.spring.sorigalpi.service;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;


import com.spring.sorigalpi.entity.VerifyCode;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PwdMailService {

	    private final EmailSenderService emailSenderService;
	    
		public boolean pwdCheckEmail(VerifyCode verifyCode) throws MessagingException {

			if(verifyCode.getCode() == null)
				return false;
			
			String emailContent = "<div>"
		            + "<p>아래에 있는 링크를 클릭하면 비밀번호 재설정 페이지로 이동합니다.</p>"
		            + "<a href='http://localhost:8080/member/find/pwd/" + verifyCode.getCode() + "'> 비밀번호 재설 링크 </a>"
		            + "</div>";

			emailSenderService.sendEmail(verifyCode.getEmail(), "소리갈피 비밀번호 재설정 이메일 입니다.", emailContent); // 이메일 전송한다.

			return true;
		}
}

