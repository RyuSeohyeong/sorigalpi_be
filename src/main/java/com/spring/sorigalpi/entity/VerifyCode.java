package com.spring.sorigalpi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.spring.sorigalpi.base.Base;
import com.spring.sorigalpi.dto.VerifyCodeDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "t_verifyCode")
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class VerifyCode extends Base{

    @Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "code")
    String code;

    @Column(name = "email")
    String email;

    public VerifyCode(VerifyCodeDto verifyDto){
        this.email = verifyDto.getEmail();
    }
}