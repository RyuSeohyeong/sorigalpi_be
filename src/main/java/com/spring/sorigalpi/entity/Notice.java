package com.spring.sorigalpi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.spring.sorigalpi.base.Base;
import com.spring.sorigalpi.dto.NoticeDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "t_notice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor 
@Builder
@Entity
@Getter
public class Notice extends Base {
	
	@Id
	private String noticeId;
	
	private String memberId;
	
	private String title;
	
	private String content;

	public void updateNotice(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
	public NoticeDto toDto() {
		return NoticeDto.builder()
				.noticeId(noticeId)
				.memberId(memberId)
				.title(title)
				.content(content)
				.build();
	}
}
