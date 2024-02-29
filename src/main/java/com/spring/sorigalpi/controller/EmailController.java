package com.spring.sorigalpi.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.sorigalpi.dto.EmailCheckDto;
import com.spring.sorigalpi.dto.EmailRequestDto;
import com.spring.sorigalpi.service.EmailSendService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EmailController {
	
    private final EmailSendService emailSendService;
    
    @PostMapping ("/mailSend")
    public String mailSend(@RequestBody @Valid EmailRequestDto emailDto){
        System.out.println("이메일 인증 요청이 있습니다.");
        System.out.println("이메일 인증 이메일 :"+emailDto.getEmail());
        
        return emailSendService.joinEmail(emailDto.getEmail());
    }
    @PostMapping("/mailauthCheck")
    public String AuthCheck(@RequestBody @Valid EmailCheckDto emailCheckDto){
    	
        Boolean Checked = emailSendService.CheckAuthNum(emailCheckDto.getEmail(),emailCheckDto.getAuthNum());
        
        if(Checked){
            return "ok";
        }
        else{
            throw new NullPointerException("오류가 발생했습니.");
        }
    }
}