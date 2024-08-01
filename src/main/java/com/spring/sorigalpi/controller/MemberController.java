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
import com.spring.sorigalpi.base.BaseResponse;
import com.spring.sorigalpi.base.BaseResponseService;
import com.spring.sorigalpi.base.ListResponse;
import com.spring.sorigalpi.base.SingleResponse;
import com.spring.sorigalpi.base.BaseException;
import com.spring.sorigalpi.dto.MemberDto;
import com.spring.sorigalpi.dto.MemberLoginDto;
import com.spring.sorigalpi.dto.TokenDto;
import com.spring.sorigalpi.entity.Member;
import com.spring.sorigalpi.exception.ErrorCode;
import com.spring.sorigalpi.exception.OtherException;
import com.spring.sorigalpi.service.EmailTokenService;
import com.spring.sorigalpi.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("memberController")
@RequestMapping(value = "/member")
@RequiredArgsConstructor
@Api(tags = "사용자")

public class MemberController {

	private final MemberService memberService;
	private final EmailTokenService emailTokenService;
	private final BaseResponseService baseResponseService;

	@ApiOperation(
	        value = "사용자 회원가입",
	        notes = "사용자가 가입을 위한 정보를 기입하고 가입한다.")
    @ApiImplicitParam(
            name = "MemberDto",
            value = "사용자 정보",
            required = true,
            dataType = "string",
            paramType = "body",
            defaultValue = "None")
	@ApiResponses({
	        @ApiResponse(code = 200, message = "회원가입 성공"),
	        @ApiResponse(code = 401, message = "권한 없음")})
	@PostMapping("/signUp")
	public BaseResponse<Object> createMember(@RequestBody MemberDto memberDto) throws BaseException, MessagingException{

			memberService.createMember(memberDto);
	        //mailTokenService.createEmailToken(memberDto.getMemberId(), memberDto.getEmail());

			return baseResponseService.responseSuccess(memberDto);
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
    public BaseResponse<Object> login(@RequestBody MemberLoginDto memberLoginDto) {
			
		TokenDto tokenDto = memberService.login(memberLoginDto);
        
			return baseResponseService.responseSuccess(tokenDto);
    }
	
	@ApiOperation(
	        value = "사용자 조회",
	        notes = "[관리자] 사용자들의 목록을 전체 조회한다.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "회원 목록 조회 성공"),
        @ApiResponse(code = 401, message = "권한 없음")})
	@GetMapping("/listMembers")
	public ListResponse<Member> listMembers() {
	   
		List<Member> members = memberService.listMembers();

	    return baseResponseService.getListResponse(members);

	}

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
	@ApiResponses({
        @ApiResponse(code = 200, message = "회원 정보 수정 성공"),
        @ApiResponse(code = 401, message = "권한 없음")})
	@PutMapping("/info")
	public BaseResponse<Object> updateMember(@AuthenticationPrincipal PrincipalDetails principalDetails,
			@RequestBody MemberDto memberDto) throws BaseException {
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
		
		return baseResponseService.responseSuccess( memberService.updateMember(memberDto, memberId));
		 
	}

	@ApiOperation(
	        value = "사용자 탈퇴",
	        notes = "사용자의 ID를 통해 탈퇴한다.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "사용자 탈퇴 성공"),
        @ApiResponse(code = 401, message = "권한 없음")})
	@DeleteMapping("/info")
	public BaseResponse<Object> deleteMember(@AuthenticationPrincipal PrincipalDetails principalDetails) throws BaseException {
		
		String memberId = principalDetails.getMember().getMemberId();
		
		return baseResponseService.responseSuccess(memberService.deleteMember(memberId));
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
	@ApiResponses({
        @ApiResponse(code = 200, message = "사용자 이메일 찾기 성공"),
        @ApiResponse(code = 401, message = "권한 없음")})
	@GetMapping("/find/email/{email}")
	public SingleResponse<Member> findMember(@PathVariable String email) throws BaseException {
		
		return baseResponseService.getSingleResponse(memberService.findMember(email));
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
	        throw new OtherException(ErrorCode.INVALID_TOKEN);
	    }
		sb.append("\n"); // \n 줄바꿈
		if (authentication != null) {
	        sb.append("authentication ");
	        sb.append(authentication);
	        sb.append("\n");
	    } else {
	        // Authentication이 null인 경우에 대한 예외 처리
	        throw new OtherException(ErrorCode.NO_AUTHORIZED);
	    }

		return sb.toString();

	}
	
	@ApiOperation(
	        value = "사용자 비밀번호 변경",
	        notes = "사용자가 비밀번호 변경 탭을 누른 후 변경한다.")
    @ApiImplicitParam(
            name = "MemberDto",
            value = "사용자 비밀번호 변경",
            required = true,
            dataType = "string",
            paramType = "body",
            defaultValue = "None")
	@ApiResponses({
        @ApiResponse(code = 200, message = "사용자 비밀번호 변경 성공"),
        @ApiResponse(code = 401, message = "권한 없음")})
	@PutMapping("/info/newPwd")
	public BaseResponse<Object> updateNewPwd(@AuthenticationPrincipal PrincipalDetails principalDetails,
			@RequestBody MemberDto memberDto) throws BaseException {
		String memberId = principalDetails.getMember().getMemberId();
		memberDto.setMemberId(memberId);
		
		if(memberDto.getPwd() == null) {
			memberDto.setPwd(principalDetails.getMember().getPwd());
		}
		
		return baseResponseService.responseSuccess(memberService.updateNewPwd(memberDto, memberId));
				
	}
}