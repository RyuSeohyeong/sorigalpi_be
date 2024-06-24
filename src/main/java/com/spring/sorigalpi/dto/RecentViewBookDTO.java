package com.spring.sorigalpi.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.spring.sorigalpi.entity.RecentViewBook;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RecentViewBookDTO {
	@ApiModelProperty(value = "책고유ID", required = true)
	private UUID bookId;
	
	@ApiModelProperty(value = "회원고유ID", required = true)
	private String memberId;
	
	@ApiModelProperty(value = "본 날짜", example = "2024-01-01", required = true)
	private LocalDateTime creDate;
	
	public RecentViewBook toEntity() {
		return RecentViewBook.builder()
				.bookId(bookId)
				.memberId(memberId)
				.creDate(creDate)
				.build();
	}
}
