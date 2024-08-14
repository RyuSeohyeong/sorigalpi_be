package com.spring.sorigalpi.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import com.spring.sorigalpi.dto.CommentDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value="댓글 정보")
@Table(name = "t_comment")
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자 생성
@AllArgsConstructor // 전체 필드에 대한 생성자
@DynamicInsert //save시 null값 배제
public class Comment {
	
	@Id
	@ApiModelProperty(value = "댓글고유번호", required = true)
	private String commentNo;
	
	@ApiModelProperty(value = "책고유Id", required = true)
	@Type(type="uuid-char")
	private UUID bookId;
	
	@ApiModelProperty(value = "부모댓글 번호", required = true)
	@ColumnDefault("0") // default값 설정
	private String parentNo;
	
	@ApiModelProperty(value = "댓글을 작성한 회원고유Id", required = true)
	private String memberId;
	
	@ApiModelProperty(value = "댓글 내용", required = true)
	private String content;
	
	@ApiModelProperty(value = "댓글 작성 날짜", example = "2024-01-01", required = true)
	@CreatedDate //db에 default값으로 dateTime이 설정되어있음
	private LocalDateTime creDate;
	
	@ApiModelProperty(value = "댓글 신고 상태", example = "YES, NO", required = true)
	@ColumnDefault("NO") // default값 설정
	private String blind;
	
	public void updateReplyContent(String content) { //댓글 수정
		this.content = content;
	}
	
	public void updateReplyBlind(String blind) { //신고 여부 수정
		this.blind = blind;
	}
	
	public CommentDTO toDTO() {
		return CommentDTO.builder()
				.commentNo(commentNo)
				.bookId(bookId)
				.parentNo(parentNo)
				.memberId(memberId)
				.content(content)
				.creDate(creDate)
				.blind(blind)
				.build();
	}
	
}
