package com.spring.sorigalpi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.sorigalpi.base.Base;
import com.spring.sorigalpi.dto.JwtTokenProvider;
import com.spring.sorigalpi.dto.MemberDto;
import com.spring.sorigalpi.entity.Member;
import com.spring.sorigalpi.enumtype.MemberEnum.Role;
import com.spring.sorigalpi.enumtype.MemberEnum.Status;
import com.spring.sorigalpi.repository.MemberRepository;
import com.spring.sorigalpi.service.MemberLoginDto.TokenResDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService extends Base {

   @Autowired
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    
    @Transactional
    public String createMember(MemberDto memberDto) { // 사용자 추가 메소드
    	memberDto.setRole(Role.ROLE_USER);
    	memberDto.setStatus(Status.ACTIVE);
    	memberDto.setMemberId(createRandomUuId());;
    	return memberRepository.save(memberDto.toEntity()).getNickName() + "님 환영합니다.";
    }
    
    public List<Member> listMembers(){ // 사용자 조회 메소드
    	return memberRepository.findAll();
    }
    @Transactional
    public String updateMember(String memberId, MemberDto memberDto) { // 사용자 정보 변경 메소드
    	//findById 메소드를 통해 값을 가져오면서 해당 값은 영속성을 가진다.
    	Member member = memberRepository.findById(memberId).orElseThrow(
    			() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
    	//값 변경
    	member.updateMember(memberDto.getEmail(), memberDto.getPwd(), memberDto.getNickName(),
    			memberDto.getProfileImg(), memberDto.getIntro());
    	return memberDto.getNickName() + "님 정보가 변경되었습니다.";
    	//트랜잭션이 끝나면서 변경된 값을 테이블에 적용
    	//update기능에서 JPA영속성 때문에 DB에 쿼리를 없애는 부분이 없으며, Entity의 값만 변경하면 별도로 update쿼리가 필요없다.
    }
    
    @Transactional
    public String deleteMember (String memberId) { // 사용자 삭제 메소드
    	memberRepository.findById(memberId).orElseThrow(() -> {
    		return new IllegalArgumentException("해당 사용자가 존재하지 않습니다.");
    	});
    	
    	memberRepository.deleteById(memberId);
    	return "탈퇴가 완료되었습니다.";
    }
    
    @Transactional
    public TokenResDto login(String email, String pwd) {
    	//Authentication 객체 생성
    	UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, pwd);
    	Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    	
    	//검증된 인증 정보로 JWT 토큰 생성
    	TokenResDto token = jwtTokenProvider.generateToken(authentication);
    	
    	return token;
    	
    }
    	} 

