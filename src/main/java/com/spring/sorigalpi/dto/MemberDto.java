package com.spring.sorigalpi.dto;

import java.time.LocalDateTime;

import com.spring.sorigalpi.entity.Member;
import com.spring.sorigalpi.enumtype.MemberEnum.Status;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "사용자")

public class MemberDto {

	@Schema(description = "사용자 고유 ID", example = "AbCdE", required = true)
	private String memberId;

	@Schema(description = "이메일", example = "abc@efg.com", required = true)
	private String email;

	@Schema(description = "비밀번호", example = "abcde123!", required = true)
	private String pwd;

	@Schema(description = "닉네임", example = "닉네임", required = true)
	private String nickName;

	@Schema(description = "프로필 사진", example = "abc.jpg")
	private String profileImg;

	@Schema(description = "자기소개", example = "안녕하세요.")
	private String intro;

	@Schema(description = "권한", example = "ADMIN, USER, GUEST, BLOCK", defaultValue = "USER", required = true)
	private String role;

	@Schema(description = "활동 상태", example = "ACTIVE, QUIT", defaultValue = "ACTIVE", required = true)
	private Status status;
	
	@Schema(description = "이메일 인증 여부", example = "0,1", defaultValue = "0", required = true)
	private boolean emailVerified;
	
	@Schema(description = "만들어진 날짜", example = "YYYY-MM-NN 00:00:00", required = true)
	private LocalDateTime creDate;
	
	@Schema(description = "수정된 날짜", example = "YYYY-MM-NN 00:00:00", required = true)
	private LocalDateTime modifiedDate;

	// Entity 클래스인 Member에 객체를 주입하여 Entity 클래스를 반환하는 메소드
	public Member toEntity() {
		return Member.builder().memberId(memberId).email(email).pwd(pwd).nickName(nickName).profileImg(profileImg)
				.intro(intro).role(role).status(status).emailVerified(emailVerified).build();
	}
	
	@Getter
	@Setter
	@Schema(description = "재설정 비밀번호")
	public static class PwdDto{
	
	@Schema(description = "재설정 비밀번호", example = "abcde123!")
	private String pwd;
	}
}