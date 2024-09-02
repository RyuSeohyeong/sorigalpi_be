package com.spring.sorigalpi.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.spring.sorigalpi.base.Base;
import com.spring.sorigalpi.dto.MemberDto;
import com.spring.sorigalpi.enumtype.MemberEnum.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "t_member")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = false) /*
										 * 객체의 직접적인 서브클래스가 아니면 super class를 호출하기 때문에 별도로 구현하는 Value Object가 없을
										 * 경우 @EqualsAndHashCode(callSuper=false) 를 선언함 - 자식 클래스의 필드를 사용하기위해
										 */

public class Member extends Base {

	@Id
	@Column(name = "memberId")
	private String memberId;

	@Column(name = "email")
	private String email;

	@Column(name = "pwd")
	private String pwd;

	@Column(name = "nickName")
	private String nickName;

	@Column(name = "profileImg")
	private String profileImg;

	@Column(name = "intro")
	private String intro;

	@Column(name = "role")
	private String role;

	@Column(name = "status")
	@Enumerated(value = EnumType.STRING)
	private Status status;
	
	@Column(name = "emailVerified")
	private boolean emailVerified;
	
	@Column(name = "creDate")
	private LocalDateTime creDate;
	
	@Column(name = "modifiedDate")
	private LocalDateTime modifiedDate;

	public void updateMember(String nickName, String profileImg,
			String intro) { /*
							 * 사용자 정보 수정 메소드 - JPA에서 영속성 컨텍스트 유지하기를 제공하는데 이 상태에서 해당 데이터 값을 변경하면 자동으로 변경사항이
							 * DB에 저장된다. 즉 데이터만 변경하면 알아서 변경되므로 수정 메소드를 만들어서 구현함
							 */
		this.nickName = nickName;
		this.profileImg = profileImg;
		this.intro = intro;
	}
	
	public void updateNewPwd(String pwd) {
		this.pwd = pwd;
	}

	public List<String> getRolesList() {
		if (this.role.length() > 0) {
			return Arrays.asList(this.role.split(","));
		}
		
		return new ArrayList<>();
	}

	public void emailVerified() { // 이메일 인증 완료
	
		this.emailVerified = true;
	}
	
	public void updatePwd(MemberDto.PwdDto requestDto) {
		this.pwd = requestDto.getPwd();
	}
	
	public MemberDto toDto() {
		return MemberDto.builder().memberId(memberId)
				.email(email)
				.pwd(pwd)
				.nickName(nickName)
				.profileImg(profileImg)
				.intro(intro)
				.role(role)
				.status(status)
				.emailVerified(emailVerified)
				.creDate(creDate)
				.modifiedDate(modifiedDate)
				.build();
	}
}