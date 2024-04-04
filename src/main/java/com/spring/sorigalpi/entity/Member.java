package com.spring.sorigalpi.entity;

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
import com.spring.sorigalpi.enumtype.MemberEnum.Status;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "회원")
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
	@ApiModelProperty(value = "사용자 고유 아이디", required = true)
	@Column(name = "memberId")
	private String memberId;

	@ApiModelProperty(value = "이메일", example = "email@email.com", required = true)
	@Column(name = "email")
	private String email;

	@ApiModelProperty(value = "비밀번호", example = "abcde12345!", required = true)
	@Column(name = "pwd")
	private String pwd;

	@ApiModelProperty(value = "닉네임", example = "닉네임", required = true)
	@Column(name = "nickName")
	private String nickName;

	@ApiModelProperty(value = "프로필 사진", example = "image.jpg", required = false)
	@Column(name = "profileImg")
	private String profileImg;

	@ApiModelProperty(value = "자기 소개", example = "안녕하세요. 반갑습니다.", required = false)
	@Column(name = "intro")
	private String intro;

	@ApiModelProperty(value = "권한", example = "ADMIN, USER, GUEST, BLOCK", required = true)
	@Column(name = "role")
	private String role;

	@ApiModelProperty(value = "활동 상태", example = "ACTIVE, QUIT", required = true)
	@Column(name = "status")
	@Enumerated(value = EnumType.STRING)
	private Status status;

	public void updateMember(String email, String pwd, String nickName, String profileImg,
			String intro) { /*
							 * 사용자 정보 수정 메소드 - JPA에서 영속성 컨텍스트 유지하기를 제공하는데 이 상태에서 해당 데이터 값을 변경하면 자동으로 변경사항이
							 * DB에 저장된다. 즉 데이터만 변경하면 알아서 변경되므로 수정 메소드를 만들어서 구현함
							 */

		this.email = email;
		this.pwd = pwd;
		this.nickName = nickName;
		this.profileImg = profileImg;
		this.intro = intro;
	}

	public List<String> getRolesList() {
		if (this.role.length() > 0) {
			return Arrays.asList(this.role.split(","));
		}
		return new ArrayList<>();
	}

}