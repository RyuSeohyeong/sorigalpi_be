package com.spring.sorigalpi.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.sorigalpi.dto.EmailCheckDto;
import com.spring.sorigalpi.dto.EmailRequestDto;
import com.spring.sorigalpi.service.MailSendService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MailController {
    private final MailSendService mailService;
    @PostMapping ("/mailSend")
    public String mailSend(@RequestBody @Valid EmailRequestDto emailDto){
        System.out.println("이메일 인증 요청이 들어옴");
        System.out.println("이메일 인증 이메일 :"+emailDto.getEmail());
        return mailService.joinEmail(emailDto.getEmail());
    }
    @PostMapping("/mailAuthCheck")
    public String AuthCheck(@RequestBody @Valid EmailCheckDto emailCheckDto){
        Boolean Checked=mailService.CheckAuthNum(emailCheckDto.getEmail(),emailCheckDto.getAuthNum());
        if(Checked){
            return "ok";
        }
        else{
            throw new NullPointerException("뭔가 잘못!");
        }
    }




}