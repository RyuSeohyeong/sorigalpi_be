package com.spring.sorigalpi.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import com.spring.sorigalpi.dto.RecentViewBookDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "최근 본 동화")
@Table(name = "t_recentviewbook")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = false)
public class RecentViewBook {
	@Id
	@ApiModelProperty(value = "책고유ID", required = true)
	@Type(type="uuid-char")
	private UUID bookId;
	
	@ApiModelProperty(value = "회원고유ID", required = true)
	@Column(name = "memberId")
	private String memberId;
	
	@ApiModelProperty(value = "본 날짜", example = "2024-01-01", required = true)
	@CreatedDate
	private LocalDateTime creDate;
	
	public RecentViewBookDTO toDTO() {
		return RecentViewBookDTO.builder()
				.bookId(bookId)
				.memberId(memberId)
				.creDate(creDate)
				.build();
	}
}
