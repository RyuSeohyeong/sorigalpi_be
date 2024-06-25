package com.spring.sorigalpi.controller;

import java.util.HashMap;

import javax.mail.MessagingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.sorigalpi.dto.MemberDto;
import com.spring.sorigalpi.dto.VerifyCodeDto;
import com.spring.sorigalpi.entity.VerifyCode;
import com.spring.sorigalpi.service.MemberService;
import com.spring.sorigalpi.service.PwdMailService;
import com.spring.sorigalpi.service.VerifyCodeService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping
public class FindPwdController {

	private final PwdMailService pwdMailService;
	private final VerifyCodeService verifyCodeService;
	private final MemberService memberService;

	@GetMapping("/member/find/pwd") // 비밀번호 찾기 재설정 메일 보내기
	public ResponseEntity<HashMap> findPwdUrl(@RequestBody VerifyCodeDto verifyCodeDto) throws MessagingException {

		VerifyCode verifyCode = verifyCodeService.saveCode(verifyCodeDto);

		boolean success = pwdMailService.pwdCheckEmail(verifyCode);

		HashMap<String, Object> responseMap = new HashMap<>();
		if (success) {
			responseMap.put("status", 200);
			responseMap.put("message", "메일 발송 성공");
			responseMap.put("code", verifyCode.getCode());
			return new ResponseEntity<HashMap>(responseMap, HttpStatus.OK);
		} else {
			responseMap.put("status", 500);
			responseMap.put("message", "메일 발송 실패");
			return new ResponseEntity<HashMap>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/member/find/pwd/{code}") // 비밀번호 재설정하기
	public ResponseEntity<HashMap> changePwd(@RequestBody MemberDto.PwdDto requestDto, @PathVariable String code) {

		VerifyCode verifyCode = verifyCodeService.findCode(code);

		HashMap<String, Object> responseMap = new HashMap<>();

		if (verifyCode == null) {
			responseMap.put("status", 401);
			responseMap.put("message", "만료되었거나 잘못된 링크입니다.");
			return new ResponseEntity<HashMap>(responseMap, HttpStatus.CONFLICT);
		}

		memberService.updatePwd(verifyCode.getEmail(), requestDto);

		if (true) {
			verifyCodeService.deleteByEmail(verifyCode.getEmail());
			responseMap.put("status", 200);
			responseMap.put("message", "비밀번호 변경 성공");

			return new ResponseEntity<HashMap>(responseMap, HttpStatus.OK);

		} else {

			responseMap.put("status", 500);
			responseMap.put("message", "비밀번호 변경 실패");
			return new ResponseEntity<HashMap>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}