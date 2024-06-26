package com.spring.sorigalpi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.sorigalpi.auth.JwtProvider;
import com.spring.sorigalpi.auth.PrincipalDetails;
import com.spring.sorigalpi.base.Base;
import com.spring.sorigalpi.dto.MemberDto;

import com.spring.sorigalpi.dto.MemberLoginDto;
import com.spring.sorigalpi.entity.Member;
import com.spring.sorigalpi.enumtype.MemberEnum.Status;
import com.spring.sorigalpi.exception.BaseException;
import com.spring.sorigalpi.exception.ErrorCode;
import com.spring.sorigalpi.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService extends Base {

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder pwdEncoder;
	private final JwtProvider jwtProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	@Transactional
	public Member createMember(MemberDto memberDto) { // 사용자 추가 메소드

		String email = memberDto.getEmail();
		
		Optional<Member> findmember = memberRepository.findByEmail(email);
		
	    if (!findmember.isPresent()) {
	      
		// memberDto에서 pwd값을 가져와 BCryptPasswordEncoder로 회원의 비밀번호를 암호화한다.
		String encodedPassword = pwdEncoder.encode(memberDto.getPwd());

		memberDto.setPwd(encodedPassword);
		memberDto.setRole("ROLE_USER");
		memberDto.setStatus(Status.ACTIVE);
		memberDto.setMemberId(createRandomUuId());
		;

		Member member = memberDto.toEntity();
		memberRepository.save(member);

		return member;
		
	    } else {
	    	
	    	throw new BaseException(ErrorCode.MEMBER_EXISTED);
	}
	    
}

	public List<Member> listMembers() { // 사용자 조회 메소드
		return memberRepository.findAll();
	}

	@Transactional
	public String updateMember(String memberId, MemberDto memberDto) { // 사용자 정보 변경 메소드
		// findById 메소드를 통해 값을 가져오면서 해당 값은 영속성을 가진다.
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new BaseException(ErrorCode.MEMBER_NOT_FOUND));

		// 변경할 비밀번호 암호화하여 저장
		String updatePwd = pwdEncoder.encode(memberDto.getPwd());
		
		
		// 값 변경
		member.updateMember(memberDto.getEmail(), updatePwd, memberDto.getNickName(),
				memberDto.getProfileImg(), memberDto.getIntro());
		return memberDto.getNickName() + "님 정보가 변경되었습니다.";
		// 트랜잭션이 끝나면서 변경된 값을 테이블에 적용
		// update기능에서 JPA영속성 때문에 DB에 쿼리를 없애는 부분이 없으며, Entity의 값만 변경하면 별도로 update쿼리가
		// 필요없다.
	}

	@Transactional
	public String deleteMember(String memberId) { // 사용자 삭제 메소드
		memberRepository.findById(memberId).orElseThrow(() -> {
			return new BaseException(ErrorCode.MEMBER_NOT_FOUND);
		});

		memberRepository.deleteById(memberId);
		return "탈퇴가 완료되었습니다.";
	}

	public String login(MemberLoginDto memberLoginDto) {

		String email = memberLoginDto.getEmail();
		String pwd = memberLoginDto.getPwd();
		
	    // 이메일 인증 여부 확인
	    Member member = memberRepository.findByEmail(email)
	        .orElseThrow(() -> new BaseException(ErrorCode.MEMBER_NOT_FOUND));

	    if (!member.isEmailVerified()) {
	        throw new BaseException(ErrorCode.EMAIL_NOT_VERIFID);
	    }

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, pwd);

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		// + JWT 인증이 완료된 객체이면,
		if (authentication.isAuthenticated()) {
			PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

			String authenticatedMemberId = principalDetails.getMember().getMemberId();
			String authenticatedEmail = principalDetails.getMember().getEmail();

			return "로그인하였습니다. " + jwtProvider.generateJwtToken(authenticatedMemberId, authenticatedEmail);
		}

		throw new BaseException(ErrorCode.NO_AUTHORIZED);
	}

	public Member findMember(String email) {
		Member member = memberRepository.findByEmail(email)
				.orElseThrow(() -> new BaseException(ErrorCode.MEMBER_NOT_FOUND));

		return member;
	}

	public String updatePwd(String email, MemberDto.PwdDto requestDto) {
		Member member = memberRepository.findByEmail(email)
				.orElseThrow(() -> new BaseException(ErrorCode.MEMBER_NOT_FOUND));
		
		requestDto.setPwd(pwdEncoder.encode(requestDto.getPwd()));
		member.updatePwd(requestDto);
		return "비밀번호가 변경되었습니다.";
	}

}
