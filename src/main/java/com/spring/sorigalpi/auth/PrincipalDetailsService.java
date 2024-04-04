package com.spring.sorigalpi.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.sorigalpi.entity.Member;
import com.spring.sorigalpi.exception.BaseException;
import com.spring.sorigalpi.exception.ErrorCode;
import com.spring.sorigalpi.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService { // UserDetailsService 인터페이스: 사용자 이름/이메일을 받아서 사용자의
																		// 인증 정보를 가져온다.

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(email)
				.orElseThrow(() -> new BaseException(ErrorCode.MEMBER_NOT_FOUND));
		return new PrincipalDetails(member);
	}

}