package com.spring.sorigalpi.controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.sorigalpi.dto.BookDTO;
import com.spring.sorigalpi.entity.Book;
import com.spring.sorigalpi.service.BookService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Api(tags = "book")
@RestController("bookController")
@RequestMapping(value="/book")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	
	@ApiOperation(
			value = "동화책 테이블의 모든 정보 조회",
			notes = "동화책 테이블의 모든 정보를 불러오는 API") 
	@ApiResponse(code = 200, message = "성공")
	@GetMapping("/getAllBook") //동화책 테이블의 모든 정보 가져오기
	public ResponseEntity<BasicResponse> allBookList(){
			 
		List<BookDTO> bookList = bookService.getAllBook();
		
		BasicResponse basicResponse =  BasicResponse.builder()
												.code(HttpStatus.OK.value())
												.httpStatus(HttpStatus.OK)
												.message("전체 사용자 조회 성공")
												.result(new ArrayList<>(bookList))
												.count(bookList.size()).build();
	
		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}
	
	@ApiOperation(
			value = "동화책 테이블 정보 생성",
			notes = "화책 테이블 정보 생성 API") 
	@ApiResponse(code = 200, message = "성공")
	@PostMapping("/createBook") //동화책 생성
	public ResponseEntity<BasicResponse> createBook(@RequestBody BookDTO bookDTO){
		String bookId = bookService.createBook(bookDTO);
		
		BasicResponse basicResponse =  BasicResponse.builder()
					.code(HttpStatus.OK.value())
					.httpStatus(HttpStatus.OK)
					.message("책 생성 성공")
					.resultStr(bookId)
					.build();

		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}
	
	
	@ApiOperation(
			value = "동화책 테이블 정보 삭제 API",
			notes = "동화책 id로 하나 삭제") 
	@ApiResponse(code = 200, message = "성공")
	@DeleteMapping("/deleteBookById") //동화책 Id로 삭제
	public ResponseEntity<BasicResponse> deleteBook(@RequestBody BookDTO bookDTO){
		String result;	
			try {
				bookService.deleteBookById(bookDTO);
				result="삭제성공";
			}catch (Exception e) {
				e.printStackTrace();
				result="삭제실패";
			}
			
		
			BasicResponse basicResponse =  BasicResponse.builder()
					.code(HttpStatus.OK.value())
					.httpStatus(HttpStatus.OK)
					.message(result)
					.build();
		
		
		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}
	
	@ApiOperation(
			value = "동화책 테이블 정보 수정 API",
			notes = "동화책 수정") 
	@PutMapping("/updateBook")
	public String updateBook() { //동화책 수정
		return "return";
	}
	
	@ApiOperation(
			value = "동화책 ID로 조회 API",
			notes = "동화책ID로 하나 조회")
	@PostMapping("/searchBookById")
	public Book searchOneBook(@RequestBody BookDTO bookDTO){ //동화책 id로 검색
		
		Book result =  bookService.findByBookId(bookDTO);
		
		return result;
	}
	
	@ApiOperation(
			value = "동화책 제목으로 조회 API",
			notes = "제목으로 모든 동화정보 리스트로 조회")
	@GetMapping("/searchByBookName/{bookName}")
	public ResponseEntity<BasicResponse> searchByBookName(@PathVariable String bookName) { //동화책 제목으로 검색
		
		List<BookDTO> bookList = bookService.findByBookName(bookName);
		
		BasicResponse basicResponse =  BasicResponse.builder()
				.code(HttpStatus.OK.value())
				.httpStatus(HttpStatus.OK)
				.message("책 제목으로 조회 성공")
				.result(new ArrayList<>(bookList))
				.count(bookList.size()).build();

		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
		
	}
	
	@PutMapping
	public void updateBook(@RequestBody BookDTO bookDTO){//동화 수정
		bookService.updateBook(bookDTO);
	}
	
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@ApiModel(value = "응답용")
	static class BasicResponse{ // response로 사용할 class
		
		private Integer code;
		private HttpStatus httpStatus;
		private String message;
		private Integer count;
		private List<Object> result;
		private String resultStr;
		
	}
	
	
}
