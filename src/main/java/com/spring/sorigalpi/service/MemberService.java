package com.spring.sorigalpi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.sorigalpi.auth.JwtProvider;
import com.spring.sorigalpi.auth.PrincipalDetails;
import com.spring.sorigalpi.base.Base;
import com.spring.sorigalpi.base.BaseException;
import com.spring.sorigalpi.dto.MemberDto;

import com.spring.sorigalpi.dto.MemberLoginDto;
import com.spring.sorigalpi.dto.TokenDto;
import com.spring.sorigalpi.entity.Member;
import com.spring.sorigalpi.enumtype.MemberEnum.Status;
import com.spring.sorigalpi.exception.ErrorCode;
import com.spring.sorigalpi.exception.OtherException;
import com.spring.sorigalpi.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService extends Base {

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder pwdEncoder;
	private final JwtProvider jwtProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final EmailTokenService emailTokenService;

	@Transactional
	public Member createMember(MemberDto memberDto) throws BaseException,  MessagingException  { // 사용자 추가 메소드

		String email = memberDto.getEmail();
		
		Optional<Member> findmember = memberRepository.findByEmail(email);
		
	    if (!findmember.isPresent()) {
	      
	    	
		// memberDto에서 pwd값을 가져와 BCryptPasswordEncoder로 회원의 비밀번호를 암호화한다.
		String encodedPassword = pwdEncoder.encode(memberDto.getPwd());

		memberDto.setMemberId(createRandomUuId());
		memberDto.setPwd(encodedPassword);
		memberDto.setRole("ROLE_USER");
		memberDto.setStatus(Status.ACTIVE);
		memberDto.setEmailVerified(true);
		memberDto.setProfileImg("No Image");
		memberDto.setIntro("No Intro");
		
		return memberRepository.save(memberDto.toEntity());
		
		//회원가입을 위한 이메일 토큰 생성 및 메일 발송
       //emailTokenService.createEmailToken(memberDto.getMemberId(), memberDto.getEmail());

		
	    } else {
	    	
	    	throw new OtherException(ErrorCode.MEMBER_EXISTED);
	}
	       
}
	
	public List<MemberDto> listMembers() { // 사용자 조회 메소드
		List<Member> memberList = memberRepository.findAll();
		List<MemberDto> memberDtoList = memberList.stream().map(Member::toDto).collect(Collectors.toList());
		
		
		return memberDtoList;
	}

	@Transactional
	public String updateMember(MemberDto memberDto,String memberId) { // 사용자 정보 변경 메소드
		
		// findById 메소드를 통해 값을 가져오면서 해당 값은 영속성을 가진다.
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new OtherException(ErrorCode.MEMBER_NOT_FOUND));	
		
		// 값 변경
		member.updateMember(memberDto.getNickName(),
				memberDto.getProfileImg(), memberDto.getIntro());
		
		return memberDto.getNickName() + "님 정보가 변경되었습니다.";
		// 트랜잭션이 끝나면서 변경된 값을 테이블에 적용
		// update기능에서 JPA영속성 때문에 DB에 쿼리를 없애는 부분이 없으며, Entity의 값만 변경하면 별도로 update쿼리가
		// 필요없다.
	}

	@Transactional
	public String deleteMember(String memberId) { // 사용자 삭제 메소드
		memberRepository.findById(memberId).orElseThrow(() -> {
			return new OtherException(ErrorCode.MEMBER_NOT_FOUND);
		});

		memberRepository.deleteById(memberId);
		
		return "탈퇴가 완료되었습니다.";
	}

	public TokenDto login(MemberLoginDto memberLoginDto) {

	    String email = memberLoginDto.getEmail();
	    String pwd = memberLoginDto.getPwd();

	    // 이메일 인증 여부 확인
	    Member member = memberRepository.findByEmail(email)
	        .orElseThrow(() -> new OtherException(ErrorCode.MEMBER_NOT_FOUND));

	    if (!member.isEmailVerified()) {
	        throw new OtherException(ErrorCode.EMAIL_NOT_VERIFID);
	    }

	    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, pwd);

	    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

	    // + JWT 인증이 완료된 객체이면,
	    if (authentication.isAuthenticated()) {
	        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

	        String authenticatedMemberId = principalDetails.getMember().getMemberId();
	        String authenticatedEmail = principalDetails.getMember().getEmail();

	        // TokenDto 객체 생성
	        TokenDto tokenDto = jwtProvider.generateJwtTokenDto(authenticatedMemberId, authenticatedEmail);

	        return tokenDto;
	    }

	    throw new OtherException(ErrorCode.NO_AUTHORIZED);
	}

	public MemberDto findMember(MemberDto memberDto) {
		
		String email = memberDto.getEmail();		
		
		Member member = memberRepository.findByEmail(email)
				.orElseThrow(()-> new OtherException(ErrorCode.MEMBER_NOT_FOUND));
	
		return member.toDto(); 
	
	}

	public String updatePwd(String email, MemberDto.PwdDto requestDto) {
		Member member = memberRepository.findByEmail(email)
				.orElseThrow(() -> new OtherException(ErrorCode.MEMBER_NOT_FOUND));
		
		requestDto.setPwd(pwdEncoder.encode(requestDto.getPwd()));
		member.updatePwd(requestDto);
		
		return "비밀번호가 변경되었습니다.";
		
	}

	@Transactional
	public String updateNewPwd(MemberDto memberDto,String memberId) {

		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new OtherException(ErrorCode.MEMBER_NOT_FOUND));

		// 변경할 비밀번호 암호화하여 저장
		String updatePwd = pwdEncoder.encode(memberDto.getPwd());
		
		// 값 변경
		member.updateNewPwd(updatePwd);
		
		return "새로운 비밀번호로 변경되었습니다.";
	}
}
