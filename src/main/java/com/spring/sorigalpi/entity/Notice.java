package com.spring.sorigalpi.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)

@Getter
public class Notice {
	
	@Id
	private String noticeId;
	
	private String memberId;
	
	private String title;
	
	private String content;
	
	@CreatedDate
	private LocalDateTime creDate;
	
	@LastModifiedDate
	private LocalDateTime modifiedDate;

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
				.creDate(creDate)
				.modifiedDate(modifiedDate)
				.build();
	}
}