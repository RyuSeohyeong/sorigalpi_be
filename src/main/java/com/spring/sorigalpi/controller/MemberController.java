package com.spring.sorigalpi.controller;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.sorigalpi.dto.MemberDto;
import com.spring.sorigalpi.dto.MemberLoginDto;
import com.spring.sorigalpi.entity.Member;
import com.spring.sorigalpi.security.JwtToken;
import com.spring.sorigalpi.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags="member")
@RestController("memberController")
@RequestMapping(value = "/member")

public class MemberController {
	
    private final MemberService memberService;
    
    public MemberController(MemberService memberService) {
    	this.memberService = memberService;
    }
 
  
	@ApiOperation(value="회원 가입", notes="회원 가입")
	@PostMapping("/signUp")
	public String createMember(@RequestBody MemberDto memberDto) {
		
		return memberService.createMember(memberDto);
	}
	
	@ApiOperation(value="로그인", notes="로그인")
	@PostMapping(value = "/login")
	public JwtToken login(@RequestBody MemberLoginDto memberLoginDto) {
        String email = memberLoginDto.getEmail();
        String pwd = memberLoginDto.getPwd();
        JwtToken jwtToken = memberService.login(email, pwd);
        return jwtToken;
    }
	
	
	@ApiOperation(value="사용자 조회", notes="사용자 조회")
	@GetMapping("/listMembers")
	public List<Member> listeMembers(){
		return (List<Member>) memberService.listMembers();
	}
	
	@ApiOperation(value="사용자 정보 변경", notes="사용자 정보 변경")
	@PutMapping("/{memberId}")
		public String updatMember( @ApiParam(name = "memberId", value = "사용자 고유 아이디", required = true) @PathVariable String memberId, @RequestBody MemberDto memberDto) {
			return memberService.updateMember(memberId, memberDto);
		}
	
	@ApiOperation(value="사용자 탈퇴", notes="사용자 탈퇴")
	@DeleteMapping("/{memberId}")
	public String deleteMember(
			@ApiParam(name = "memberId", value = "사용자 고유 아이디", required = true)
			@PathVariable String memberId) {
		return memberService.deleteMember(memberId);
	}
}
