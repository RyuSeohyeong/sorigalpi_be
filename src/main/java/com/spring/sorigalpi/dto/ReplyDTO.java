package com.spring.sorigalpi.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;

import com.spring.sorigalpi.entity.Reply;

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
public class ReplyDTO {
	@ApiModelProperty(value = "댓글고유번호", required = true)
	private String replyNo;
	
	@ApiModelProperty(value = "책고유Id", required = true)
	private UUID bookId;
	
	@ApiModelProperty(value = "부모댓글 번호", required = true)
	private String parentNo;
	
	@ApiModelProperty(value = "댓글을 작성한 회원고유Id", required = true)
	private String memberId;
	
	@ApiModelProperty(value = "댓글 내용", required = true)
	private String content;
	
	@ApiModelProperty(value = "댓글 작성 날짜", example = "2024-01-01", required = true)
	@CreatedDate
	private LocalDateTime creDate;
	
	@ApiModelProperty(value = "댓글 신고 상태", example = "YES, NO", required = true)
	private String blind;
	
	public Reply toEntity() {
		return Reply.builder()
				.replyNo(replyNo)
				.bookId(bookId)
				.parentNo(parentNo)
				.memberId(memberId)
				.content(content)
				.creDate(creDate)
				.blind(blind)
				.build();
	}
}
