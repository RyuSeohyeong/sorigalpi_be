package com.spring.sorigalpi.service;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSendService {
    @Autowired
    private JavaMailSender mailSender;
    private int authNumber;
    
    private RedisUtil redisUtil;
    
    public boolean CheckAuthNum(String email,String authNum){
        if(redisUtil.getData(authNum)==null){
            return false;
        }
        else if(redisUtil.getData(authNum).equals(email)){
             return true;
         }
         else{
             return false;
         }
    }

    public void makeRandomNumber() {
        Random r = new Random();
        String randomNumber = "";
        for(int i = 0; i < 6; i++) {
            randomNumber += Integer.toString(r.nextInt(10));
        }

        authNumber = Integer.parseInt(randomNumber);
    }


    public String joinEmail(String email) {
        makeRandomNumber();
        String setFrom = "test@gmail.com"; 
        String toMail = email;
        String title = "회원 가입 인증 이메일 입니다.";
        String content =
                "소리갈피에 회원가입 인증번호 입니다." + 
                        "<br><br>" +
                        "인증 번호는 " + authNumber + "입니다." +
                        "<br>" +
                        "인증번호를 제대로 입력해주세요";
        mailSend(setFrom, toMail, title, content);
        return Integer.toString(authNumber);
    }

   
    public void mailSend(String setFrom, String toMail, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
         
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content,true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        

    }

}