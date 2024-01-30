package com.spring.sorigalpi.entity; 

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.spring.sorigalpi.base.Base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table ( name = "t_member" )
@Entity
@Data
@EqualsAndHashCode(callSuper=false) /* 객체의 직접적인 서브클래스가 아니면 super class를 호출하기 때문에 
별도로 구현하는 Value Object가 없을 경우 @EqualsAndHashCode(callSuper=false) 를 선언함 - 자식 클래스의 필드를 사용하기위해 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends Base {

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


    @Column( name = "role" )
    private String role;
    @Column( name = "status" )
    private String status;


    public void updateMember (String email, String pwd, String nickName, String profileImg,
            String intro, String status, String role) { /*사용자 정보 수정 메소드 - JPA에서 영속성 컨텍스트 유지하기를 제공하는데 이 상태에서 해당 데이터 값을 변경하면 자동으로 변경사항이 DB에 저장된다.
    	                                                즉 데이터만 변경하면 알아서 변경되므로 수정 메소드를 만들어서 구현함*/

        this.email = email;
        this.pwd = pwd;
        this.nickName = nickName;
        this.profileImg = profileImg;
        this.intro = intro;
        this.status = status;
        this.role = role;
    }


}