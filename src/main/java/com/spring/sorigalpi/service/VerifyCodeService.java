package com.spring.sorigalpi.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.spring.sorigalpi.dto.VerifyCodeDto;
import com.spring.sorigalpi.entity.VerifyCode;
import com.spring.sorigalpi.repository.VerifyCodeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class VerifyCodeService {
	
	private final VerifyCodeRepository verifyRepository;
	
	public VerifyCode saveCode(VerifyCodeDto verifyCodeDto) { // 비밀번호 재설정을 위한 코드 저장
		
		if(verifyRepository.existsByEmail(verifyCodeDto.getEmail())) // 이메일이 존재한다면
			verifyRepository.deleteByEmail(verifyCodeDto.getEmail()); // 지우고
		
		return verifyRepository.save(new VerifyCode(verifyCodeDto)); // 다시 저장
	}

	
	public VerifyCode findCode(String code) {
		
		VerifyCode verifyCode = verifyRepository.findByCode(code); // 비밀번호 재설정을 위한 코드 검색
		
		if(verifyCode != null)
				verifyRepository.deleteByEmail(verifyCode.getEmail()); // 코드가 없다면 이메일을 찾아서 삭제
				
		return verifyCode;
	}
	
	 public void deleteByEmail(String email){ // 이메일로 찾아서 삭제
	        verifyRepository.deleteByEmail(email);
	    }
}
