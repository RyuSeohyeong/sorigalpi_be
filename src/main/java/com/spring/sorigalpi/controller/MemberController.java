package com.spring.sorigalpi.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.sorigalpi.auth.PrincipalDetails;
import com.spring.sorigalpi.dto.MemberDto;
import com.spring.sorigalpi.dto.MemberLoginDto;
import com.spring.sorigalpi.entity.Member;
import com.spring.sorigalpi.exception.BaseException;
import com.spring.sorigalpi.exception.ErrorCode;
import com.spring.sorigalpi.service.EmailTokenService;
import com.spring.sorigalpi.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("memberController")
@RequestMapping(value = "/member")
@RequiredArgsConstructor
@Api(tags = "사용자")

public class MemberController {

	private final MemberService memberService;
	private final EmailTokenService emailTokenService;

	@ApiOperation(
	        value = "사용자 회원가입",
	        notes = "사용자가 가입을 위한 정보를 기입하고 가입한다.")
    @ApiImplicitParam(
            name = "MemberDto",
            value = "사용자 정보",
            required = true,
            paramType = "body",
            defaultValue = "None")
	@PostMapping("/signUp")
	public String createMember(@RequestBody MemberDto memberDto) throws MessagingException {

		memberService.createMember(memberDto);
		// emailTokenService.createEmailToken(memberDto.getMemberId(), memberDto.getEmail());
		
		return "회원 가입이 완료되었습니다.";
		
	}

	@ApiOperation(
	        value = "사용자 로그인",
	        notes = "사용자가 이메일과 비밀번호를 입력하여 로그인한다.")
    @ApiImplicitParam(
            name = "MemberLoginDto",
            value = "사용자 로그인",
            required = true,
            paramType = "body",
            defaultValue = "None")
	@PostMapping("/login")
	public String login(@RequestBody MemberLoginDto memberLoginDto) {

		return memberService.login(memberLoginDto);
	}

	@ApiOperation(
	        value = "사용자 조회",
	        notes = "[관리자] 사용자들의 목록을 전체 조회한다.")
	@GetMapping("/listMembers")
	public List<Member> listeMembers() {
		return (List<Member>) memberService.listMembers();
	}


	// 이 경로로 모든 것을 바꾸게 하지 않고 어떤 정보를 변경할지에 따라 경로를 다르게 설정하는 게 좋지 않을까요
	// 프로필을 변경할 때는 PutMapping("/profile")로 해서 원래 유저 정보에서 nickName과 intro, profileImg부분만 바꾼다든지...
	// 패스워드 변경 때는 PutMapping("/password")라는 경로로 해서 password부분만 바꾸는 코드 짠다든지...

	// 현재 코드로는 프로필 변경 때 password와 이메일도 다시 설정하고, password 변경 때에 프로필과 이메일을 다시 설정합니다.
	// 설정값이 하나라도 null이라면 에러가 납니다. 모두 DB에서 필수값이기 때문에...
	// 그렇기 때문에 이대로 한다면 굳이 프론트에서 목적과 상관없는 정보까지 MemberDTO에 담아서 주거나, 혹은 백에서 프론트가 준 값 중에 null이 있는지 하나하나 다 체크해야 합니다(아래의 if코드가 그겁니다).
	// 그렇게 해도 굴러가긴 하겠지만? 굳이? 그럴 필요는? 없지 않나? 싶은? 쓸모없는 정보 주면 보안적으로 좋지 않기도 하고? password를 암호화할 때 CPU가 메모리 냠냠하는데? 굳이? 간식 줘서 CPU 살찌울 필요는 없지 않나?
	@ApiOperation(
	        value = "사용자 정보 변경",
	        notes = "사용자의 ID를 통해 정보를 변경한다.")
    @ApiImplicitParams({
    	@ApiImplicitParam(
                name = "MemberDto",
                value = "사용자 정보",
                required = true,
                dataType = "string",
                paramType = "body",
                defaultValue = "None")})
	@PutMapping("/info")
	public String updateMember(@AuthenticationPrincipal PrincipalDetails principalDetails,
			@RequestBody MemberDto memberDto) {
		String memberId = principalDetails.getMember().getMemberId();
		memberDto.setMemberId(memberId);
		if(memberDto.getNickName() == null) {
			memberDto.setNickName(principalDetails.getMember().getNickName());
		}
		if(memberDto.getIntro() == null) {
			memberDto.setIntro(principalDetails.getMember().getIntro());
		}
		if (memberDto.getProfileImg() == null) {
			memberDto.setProfileImg(principalDetails.getMember().getProfileImg());
		}
		if(memberDto.getEmail() == null) {
			memberDto.setEmail(principalDetails.getMember().getEmail());
		}
		if(memberDto.getPwd() == null) {
			memberDto.setPwd(principalDetails.getMember().getPwd());
		}
		return memberService.updateMember(memberDto);
	}

	@ApiOperation(
	        value = "사용자 탈퇴",
	        notes = "사용자의 ID를 통해 탈퇴한다.")
	@DeleteMapping("/info")
	public String deleteMember(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		String memberId = principalDetails.getMember().getMemberId();
		return memberService.deleteMember(memberId);
	}

	@ApiOperation(
	        value = "사용자 이메일 찾기",
	        notes = "[관리자] 사용자가 사용하는 이메일을 통해 사용자 정보를 조회한다.")
	   @ApiImplicitParam(
               name = "email",
               value = "이메일",
               required = true,
               dataType = "string",
               paramType = "path",
               defaultValue = "None")
	@GetMapping("/find/email/{email}")
	public Member findMember(@PathVariable String email) {
		return memberService.findMember(email);
	}

	@ApiOperation(
	        value = "사용자 JWT Token인증 확인",
	        notes = "사용자의 JWT를 통해 인증되었는지 확인한다.")
	@GetMapping("/jwtTokenInfo")
	public String info(@AuthenticationPrincipal PrincipalDetails principalDetails, Authentication authentication) {

		StringBuilder sb = new StringBuilder();
	    if (principalDetails != null) {
	        sb.append("PrincipalDetails ");
	        sb.append(principalDetails);
	        sb.append("\n");
	    } else {
	        // PrincipalDetails가 null인 경우에 대한 예외 처리
	        throw new BaseException(ErrorCode.INVALID_TOKEN);
	    }
		sb.append("\n"); // \n 줄바꿈
		if (authentication != null) {
	        sb.append("authentication ");
	        sb.append(authentication);
	        sb.append("\n");
	    } else {
	        // Authentication이 null인 경우에 대한 예외 처리
	        throw new BaseException(ErrorCode.NO_AUTHORIZED);
	    }

		return sb.toString();

	}
}
