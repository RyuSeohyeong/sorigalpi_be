package com.spring.sorigalpi.service;


import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.sorigalpi.base.Base;
import com.spring.sorigalpi.dto.MemberDto;
import com.spring.sorigalpi.dto.MemberLoginDto;
import com.spring.sorigalpi.entity.Member;
import com.spring.sorigalpi.enumtype.MemberEnum.Role;
import com.spring.sorigalpi.enumtype.MemberEnum.Status;
import com.spring.sorigalpi.exception.BaseException;
import com.spring.sorigalpi.exception.ErrorCode;
import com.spring.sorigalpi.repository.MemberRepository;
import com.spring.sorigalpi.token.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService extends Base {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder pwdEncoder;
    private final JwtProvider jwtProvider;
    
    @Transactional
    public Member createMember(MemberDto memberDto) { // 사용자 추가 메소드
    
    	//memberDto에서 pwd값을 가져와 BCryptPasswordEncoder로 회원의 비밀번호를 암호화한다.
    	String encodedPassword = pwdEncoder.encode(memberDto.getPwd());

    	memberDto.setPwd(encodedPassword);
    	memberDto.setRole(Role.ROLE_USER);
    	memberDto.setStatus(Status.ACTIVE);
    	memberDto.setMemberId(createRandomUuId());;
    
    	Member member = memberDto.toEntity();
    	 memberRepository.save(member);
   
    	return member;
    }
    
    public List<Member> listMembers(){ // 사용자 조회 메소드
    	return memberRepository.findAll();
    }
    
    @Transactional
    public String updateMember(String memberId, MemberDto memberDto) { // 사용자 정보 변경 메소드
    	//findById 메소드를 통해 값을 가져오면서 해당 값은 영속성을 가진다.
    	Member member = memberRepository.findById(memberId).orElseThrow(
    			() -> new BaseException(ErrorCode.MEMBER_NOT_FOUND));
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
    		return new BaseException(ErrorCode.MEMBER_NOT_FOUND);
    	});
    	
    	memberRepository.deleteById(memberId);
    	return "탈퇴가 완료되었습니다.";
    }

    public String login(MemberLoginDto memberLoginDto) {
        String email = memberLoginDto.getEmail();
        String loginPwd = memberLoginDto.getPwd();
        
        // Optional은 값이 있을 수도 있고 없을 수도 있는 값을 감싸는 래퍼 클래스 >> loginEmail이 가리키는 객체에 직접적으로 접근 불가능
        Optional<Member> loginEmail = memberRepository.findByEmail(email);
        
        if (loginEmail.isPresent()) {
            Member member = loginEmail.get(); // Optional에서 값을 꺼내야한다. >> Optional에서 값을 꺼내는 방법으로는 get() 메서드를 사용
            if (pwdEncoder.matches(loginPwd, member.getPwd())) {
                // 회원이 로그인한 이메일로 회원을 찾은 후에 비밀번호를 비교하고, 로그인할 때 사용한 비밀번호가 일치하는 경우 JWT 토큰을 생성하여 반환
                String jwtToken = jwtProvider.generateJwtToken(member.getMemberId(), member.getEmail());
                
                return "로그인 되었습니다." + jwtToken;
            } else {
                return "비밀번호가 일치하지 않습니다.";
            }
        } else {
        	throw new BaseException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }
	
	public Member findMember(String email) { 
	 Member member = memberRepository.findByEmail(email).orElseThrow(() -> new BaseException(ErrorCode.MEMBER_NOT_FOUND));
	    
	    return member;
	}
	
}

