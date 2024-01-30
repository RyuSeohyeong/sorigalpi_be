package com.spring.sorigalpi.dto;

import com.spring.sorigalpi.entity.Member;
import com.spring.sorigalpi.enumtype.MemberEnum.Role;
import com.spring.sorigalpi.enumtype.MemberEnum.Status;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class MemberDto {
	
	@ApiModelProperty(value = "사용자 고유 아이디", required = true)
	private String memberId;
	
	@ApiModelProperty(value = "이메일", example = "email@email.com", required = true)
	private String email;
	
	@ApiModelProperty(value = "비밀번호", example = "abcde12345!", required = true)
	private String pwd;
	
	@ApiModelProperty(value = "닉네임", example = "닉네임", required = true)
	private String nickName;
	
	@ApiModelProperty(value = "프로필 사진", example = "image.jpg", required = false)
	private String profileImg;
	
	@ApiModelProperty(value = "자기 소개", example = "안녕하세요. 반갑습니다.", required = false)
	private String intro;
	
	@ApiModelProperty(value = "권한", example = "ADMIN, USER, GUEST, BLOCK", required = true)
	private Role role;
	
	@ApiModelProperty(value = "활동 상태", example = "ACTIVE, QUIT", required = true)
	private Status status;

	@Builder
	public MemberDto(String memberId, String email, String pwd, String nickName, String profileImg, String intro,
			Role role, Status status) {

		this.memberId = memberId;
		this.email = email;
		this.pwd = pwd;
		this.nickName = nickName;
		this.profileImg = profileImg;
		this.intro = intro;
		this.role = role;
		this.status = status;
	}

	// Entity 클래스인 Member에 객체를 주입하여 Entity 클래스를 반환하는 메소드
	public Member toEntity() {
		return Member.builder().memberId(memberId).email(email).pwd(pwd).nickName(nickName).profileImg(profileImg)
				.intro(intro).role(role).status(status).build();
	}
}