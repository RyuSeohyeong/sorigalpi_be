package com.spring.sorigalpi.service;

import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

import com.spring.sorigalpi.dto.MemberDto;
import com.spring.sorigalpi.entity.Member;
import com.spring.sorigalpi.entity.VerifyCode;
import com.spring.sorigalpi.exception.ErrorCode;
import com.spring.sorigalpi.exception.OtherException;
import com.spring.sorigalpi.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PwdMailService {

	    private final EmailSenderService emailSenderService;
	    private final MemberRepository memberRepository;
	    
		public boolean pwdCheckEmail(VerifyCode verifyCode) throws MessagingException {
		
			String email = verifyCode.getEmail();
			
			Optional<Member> findmember = memberRepository.findByEmail(email);
			
		    if (findmember.isPresent()) {
		    	
		    
		    	if(verifyCode.getCode() == null)
		    		return false;
			
		    	String emailContent = "<div>"
		            + "<p>아래에 있는 링크를 클릭하면 비밀번호 재설정 페이지로 이동합니다.</p>"
		            + "<a href='http://localhost:8080/member/find/pwd/" + verifyCode.getCode() + "'> 비밀번호 재설정 링크 </a>"
		            + "</div>";

		    	emailSenderService.sendEmail(verifyCode.getEmail(), "소리갈피 비밀번호 재설정 이메일 입니다.", emailContent); // 이메일 전송한다.

		    	return true;
		    	
		    } else {
		    	throw new OtherException(ErrorCode.MEMBER_NOT_FOUND);
		}
	}
}

