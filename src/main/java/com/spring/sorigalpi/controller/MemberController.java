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
		emailTokenService.createEmailToken(memberDto.getMemberId(), memberDto.getEmail());
		
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

	@ApiOperation(
	        value = "사용자 정보 변경",
	        notes = "사용자의 ID를 통해 정보를 변경한다.")
    @ApiImplicitParams({
    	@ApiImplicitParam(
            name = "memeberId",
            value = "사용자 고유 ID",
            required = true,
            dataType = "string",
            paramType = "path",
            defaultValue = "None"),
    	@ApiImplicitParam(
                name = "MemberDto",
                value = "사용자 정보",
                required = true,
                dataType = "string",
                paramType = "body",
                defaultValue = "None")})
	@PutMapping("/info/{memberId}")
	public String updatMember( @PathVariable String memberId,
			@RequestBody MemberDto memberDto) {
		return memberService.updateMember(memberId, memberDto);
	}

	@ApiOperation(
	        value = "사용자 탈퇴",
	        notes = "사용자의 ID를 통해 탈퇴한다.")
   @ApiImplicitParam(
                name = "memberId",
                value = "사용자 고유 ID",
                required = true,
                dataType = "string",
                paramType = "path",
                defaultValue = "None")
	@DeleteMapping("/info/{memberId}")
	public String deleteMember(@PathVariable String memberId) {
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
