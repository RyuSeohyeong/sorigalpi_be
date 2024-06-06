package com.spring.sorigalpi.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.spring.sorigalpi.entity.EmailToken;
import com.spring.sorigalpi.entity.Member;
import com.spring.sorigalpi.exception.BaseException;
import com.spring.sorigalpi.exception.ErrorCode;
import com.spring.sorigalpi.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailService {

	private final EmailTokenService emailTokenService;
	private final MemberRepository memberRepository;
    
	@Transactional
	public boolean verifyEmail(String token) throws BaseException { // 토큰을 사용해 이메일 인증을 한다.

		EmailToken findEmailToken = emailTokenService.findByEmailTokenIdIdAndExpiredDateAfterAndExpired(token); // 이메일 토큰을 찾는다.

		Optional<Member> findMember = memberRepository.findById(findEmailToken.getMemberId()); // 사용자 정보를 이용하여 인증 정보를 가져온다.
		findEmailToken.usedEmailToken();  // 토큰을 사용했다고 변경한다.
		
		if (findMember.isPresent()) {
			Member member = findMember.get(); // 사용자가 있다면 인증 유무에 대해 인증이 완료되었다고 변경한다.
			member.emailVerified();
			return true;
		} else {
			throw new BaseException(ErrorCode.TOKEN_NOT_FOUND);
		}
	}
}