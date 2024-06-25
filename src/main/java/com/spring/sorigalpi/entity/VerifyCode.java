package com.spring.sorigalpi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.spring.sorigalpi.base.Base;
import com.spring.sorigalpi.dto.VerifyCodeDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "t_verifyCode")
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "비밀번호 재설정을 위한 코드")

public class VerifyCode extends Base{

    @Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
    @ApiModelProperty(value = "비밀번호 재설정을 위한 코드", required = true)
    @Column(name = "code")
    String code;

    @ApiModelProperty(value = "이메일", required = true)
    @Column(name = "email")
    String email;

    public VerifyCode(VerifyCodeDto verifyDto){
        this.email = verifyDto.getEmail();
    }
}