package com.spring.sorigalpi.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.spring.sorigalpi.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = "member")
@RestController("memberController")
@RequestMapping(value = "/member")
@RequiredArgsConstructor

public class MemberController {

	private final MemberService memberService;

	@ApiOperation(value = "회원 가입", notes = "회원 가입")
	@PostMapping("/signUp")
	public Member createMember(@RequestBody MemberDto memberDto) {

		return memberService.createMember(memberDto);
	}

	@ApiOperation(value = "로그인", notes = "로그인")
	@PostMapping("/login")
	public String login(@RequestBody MemberLoginDto memberLoginDto) {

		return memberService.login(memberLoginDto);
	}

	@ApiOperation(value = "사용자 조회", notes = "사용자 조회")
	@GetMapping("/listMembers")
	public List<Member> listeMembers() {
		return (List<Member>) memberService.listMembers();
	}

	@ApiOperation(value = "사용자 정보 변경", notes = "사용자 정보 변경")
	@PutMapping("/{memberId}")
	public String updatMember(
			@ApiParam(name = "memberId", value = "사용자 고유 아이디", required = true) @PathVariable String memberId,
			@RequestBody MemberDto memberDto) {
		return memberService.updateMember(memberId, memberDto);
	}

	@ApiOperation(value = "사용자 탈퇴", notes = "사용자 탈퇴")
	@DeleteMapping("/{memberId}")
	public String deleteMember(
			@ApiParam(name = "memberId", value = "사용자 고유 아이디", required = true) @PathVariable String memberId) {
		return memberService.deleteMember(memberId);
	}

	@ApiOperation(value = "사용자 email로 찾기", notes = "사용자 email로 찾기")

	@GetMapping("/{email}")
	public Member findMember(@PathVariable String email) {
		return memberService.findMember(email);
	}

	@ApiOperation(value = "사용자 JWT Token인증 확인", notes = "사용자 JWT Token인증 확인")
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
	        // PrincipalDetails가 null인 경우에 대한 예외 처리
	        throw new BaseException(ErrorCode.INVALID_TOKEN);
	    }

		return sb.toString();

	}
}
