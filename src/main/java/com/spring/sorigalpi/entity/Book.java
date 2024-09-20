package com.spring.sorigalpi.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.sorigalpi.dto.BookDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value="책정보")
@Data
@Entity
@EntityListeners(AuditingEntityListener.class )
@Table(name = "t_book")
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자 생성
@AllArgsConstructor // 전체 필드에 대한 생성자
@Getter
@Builder
public class Book {
	@Id
	@ApiModelProperty(value = "책고유Id", required = true)
	@Type(type="uuid-char")
	private UUID bookId;
	
	@ApiModelProperty(value = "회원고유Id", required = true)
	private String memberId;
	
	@ApiModelProperty(value = "책이름", example = "토끼와 호랑이", required = true)
	private String bookName;
	
	@ApiModelProperty(value = "책페이지수", example = "100", required = true)
	private int pageNum;
	
	@ApiModelProperty(value = "공개여부", example = "PUBLIC, PRIVATE, TEMP", required = true)
	//@Enumerated(EnumType.STRING)
	private String status;
	
	@ApiModelProperty(value = "만든날짜", example = "2024-01-01", required = true)
	@CreatedDate
	private LocalDateTime creDate;
	
	@ApiModelProperty(value = "동화책 신고상태", example = "YES, NO", required = true)
	//@Enumerated(EnumType.STRING)
	private String blind;
	
	@ApiModelProperty(value = "녹음허용여부", example = "YES, NO", required = true)
	//@Enumerated(EnumType.STRING)
	private String recordable;
	
	@ApiModelProperty(value = "동화책설명", example = "동화책내용 들어감", required = true)
	private String info;
	
	public void updateBook(String bookName, int pageNum, String status, String blind,
			String recordable, String info) { 
		this.bookName = bookName;
		this.pageNum = pageNum;
		this.status = status;
		this.blind = blind;
		this.recordable = recordable;
		this.info = info;
	}
	
	public BookDTO toDTO() {
		return BookDTO.builder()
				.bookId(bookId)
				.memberId(memberId)
				.bookName(bookName)
				.pageNum(pageNum)
				.status(status)
				.creDate(creDate)
				.blind(blind)
				.recordable(recordable)
				.info(info)
				.build();
				
	}

	

}
