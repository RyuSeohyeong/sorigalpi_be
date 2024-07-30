package com.spring.sorigalpi.controller;

import java.util.HashMap;

import javax.mail.MessagingException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.sorigalpi.base.BaseException;
import com.spring.sorigalpi.base.BaseResponse;
import com.spring.sorigalpi.base.BaseResponseService;
import com.spring.sorigalpi.base.BaseResponseStatus;
import com.spring.sorigalpi.dto.MemberDto;
import com.spring.sorigalpi.dto.VerifyCodeDto;
import com.spring.sorigalpi.entity.VerifyCode;
import com.spring.sorigalpi.service.MemberService;
import com.spring.sorigalpi.service.PwdMailService;
import com.spring.sorigalpi.service.VerifyCodeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping
@Api(tags = "비밀번호 찾기")

public class FindPwdController {

	private final PwdMailService pwdMailService;
	private final VerifyCodeService verifyCodeService;
	private final MemberService memberService;
	private final BaseResponseService baseResponseService;

	@ApiOperation(
	        value = "사용자 비밀번호 재설정 메일 전송",
	        notes = "사용자가 비밀번호 찾기를 통해 재설정 메일을 보낸다.")
    @ApiImplicitParam(
            name = "VerifyCodeDto",
            value = "사용자 비밀번호 재설정을 위한 코드",
            required = true,
            dataType = "string",
            paramType = "body",
            defaultValue = "None")
	@GetMapping("/member/find/pwd") // 비밀번호 찾기 재설정 메일 보내기
	public BaseResponse<Object> findPwdUrl(@RequestBody VerifyCodeDto verifyCodeDto) throws MessagingException, BaseException {

		VerifyCode verifyCode = verifyCodeService.saveCode(verifyCodeDto);

		boolean success = pwdMailService.pwdCheckEmail(verifyCode);
		
		if (success) {
			return baseResponseService.responseSuccess();
		} else {
			return baseResponseService.responseFail(BaseResponseStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(
	        value = "사용자 비밀번호 재설정",
	        notes = "사용자가 비밀번호 재설정을 통한 발급 받은 코드로 비밀번호를 재설정한다.")
    @ApiImplicitParams({
    	@ApiImplicitParam(
            name = "MemberDto.PwdDto",
            value = "사용자가 변경할 비밀번호",
            required = true,
            dataType = "string",
            paramType = "body",
            defaultValue = "None"),
    	@ApiImplicitParam(
                name = "code",
                value = "비밀번호 재설정을 통해 발급받은 코드",
                required = true,
                dataType = "string",
                paramType = "path",
                defaultValue = "None")})
	@PostMapping("/member/find/pwd/{code}") // 비밀번호 재설정하기
	public BaseResponse<Object> changePwd(@RequestBody MemberDto.PwdDto requestDto, @PathVariable String code) throws BaseException{

		VerifyCode verifyCode = verifyCodeService.findCode(code);

		HashMap<String, Object> responseMap = new HashMap<>();

		if (verifyCode == null) {
			
			return baseResponseService.responseFail(BaseResponseStatus.NO_AUTHORIZED);
		}

		memberService.updatePwd(verifyCode.getEmail(), requestDto);

		if (true) {
			
			verifyCodeService.deleteByEmail(verifyCode.getEmail());

			return baseResponseService.responseSuccess();

		} else {
			
		return baseResponseService.responseFail(BaseResponseStatus.INTERNAL_SERVER_ERROR);	
		
		}
	}

}