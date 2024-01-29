package com.spring.sorigalpi.entity;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.spring.sorigalpi.enumtype.MemberEnum.Role;
import com.spring.sorigalpi.enumtype.MemberEnum.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table ( name = "t_member" )
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @Column( name = "memberId" )
    private String memberId;

    @Column( name = "email" )
    private String email;
    @Column( name = "pwd" )
    private String pwd;
    @Column( name = "nickName" )
    private String nickName;


    @Column( name = "profileImg" )
    private String profileImg;
    @Column( name = "intro" )
    private String intro;

    @Column( name = "creDate" )
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creDate;


   
    @Column( name = "role" )
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @Column( name = "status" )
    @Enumerated(EnumType.STRING)
    private Status status;

    public void update (String email, String pwd, String nickName, String profileImg,
            String intro, Role role, Status status) {

        this.email = email;
        this.pwd = pwd;
        this.nickName = nickName;
        this.profileImg = profileImg;
        this.intro = intro;
        this.role = role;
        this.status = status;
    }


}