package com.spring.sorigalpi.dto;

import java.util.UUID;

import com.spring.sorigalpi.entity.Book;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookDTO {

	@ApiModelProperty(value = "책고유Id", required = true)
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
	
	//@ApiModelProperty(value = "만든날짜", example = "2024-01-01", required = true)
	//private Date creDate;
	
	@ApiModelProperty(value = "동화책 신고상태", example = "YES, NO", required = true)
	//@Enumerated(EnumType.STRING)
	private String blind;
	
	@ApiModelProperty(value = "녹음허용여부", example = "YES, NO", required = true)
	//@Enumerated(EnumType.STRING)
	private String recordable;
	
	@ApiModelProperty(value = "동화책설명", example = "동화책내용 들어감", required = true)
	private String info;

	public Book toEntity() {
		return Book.builder()
				.bookId(bookId)
				.memberId(memberId)
				.bookName(bookName)
				.pageNum(pageNum)
				.status(status)
				.blind(blind)
				.recordable(recordable)
				.info(info)
				.build();
	}
	/*
	private enum Status {
		PUBLIC, PRIVATE, TEMP
	}
	
	private enum Blind {
		YES, NO
	}
	
	private enum Recordable{
		YES, NO
	}
	*/
}
