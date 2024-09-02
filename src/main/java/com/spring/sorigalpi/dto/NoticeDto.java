package com.spring.sorigalpi.dto;

import com.spring.sorigalpi.entity.Notice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "공지사항")
public class NoticeDto {
	
	@Schema(description = "공지사항 고유 ID", example = "AbCdE", required = true)
	private String noticeId;
	
	@Schema(description = "사용자 고유 ID", example = "AbCdE", required = true)
	private String memberId;
	
	@Schema(description = "공지사항 제목", example = "공지사항 제목", required = true)
	private String title;

	@Schema(description = "공지사항 내용", example = "공지사항 내용", required = true)
	private String content;
	
	public Notice toEntity() {
		return Notice.builder()
				.noticeId(noticeId)
				.memberId(memberId)
				.title(title)
				.content(content)
				.build();
	}

}
