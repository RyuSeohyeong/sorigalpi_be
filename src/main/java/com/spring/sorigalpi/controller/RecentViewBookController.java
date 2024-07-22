package com.spring.sorigalpi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.sorigalpi.controller.BookController.BasicResponse;
import com.spring.sorigalpi.dto.RecentViewBookDTO;
import com.spring.sorigalpi.service.RecentViewBookService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@Api(tags = "RecentViewBook")
@RestController("RecentViewBookController")
@RequestMapping(value="/recentViewBook")
public class RecentViewBookController {
	
	@Autowired
	private RecentViewBookService recentViewBookService;
	
	@ApiOperation(
			value = "최근 본 동화 테이블의 모든 정보 조회",
			notes = "최근 본 동화 테이블의 모든 정보를 불러오는 API") 
	@ApiResponse(code = 200, message = "성공")
	@GetMapping("/getAllRecentViewBook") //최근 본 동화 목록 불러오기
	public ResponseEntity<BasicResponse> allRecentViewBookList(){
		String reMessage;
		List<RecentViewBookDTO> dtoList = recentViewBookService.getAllRecentViewBookList();
		
		if (dtoList.size() <= 0) {
			reMessage = "최근 본 동화 없음";
		}else {
			reMessage = "최근 본 동화 목록 조회 성공";
		}
		
		BasicResponse basiceResponse = BasicResponse.builder()
				.code(HttpStatus.OK.value())
				.httpStatus(HttpStatus.OK)
				.message(reMessage)
				.result(new ArrayList<>(dtoList))
				.count(dtoList.size()).build();
		return new ResponseEntity<>(basiceResponse, HttpStatus.OK);
	}
	
	@ApiOperation(
			value = "최근 본 동화 추가",
			notes = "최근 본 동화 추가 API") 
	@ApiResponse(code = 200, message = "성공")
	@PostMapping("/createReadRecentBook") //최근 본 동화 추가
	public ResponseEntity<BasicResponse> createBook(@RequestBody RecentViewBookDTO dto){
		
		RecentViewBookDTO RVBdto = recentViewBookService.createReadRecentBook(dto);
		
		BasicResponse basicResponse =  BasicResponse.builder()
					.code(HttpStatus.OK.value())
					.httpStatus(HttpStatus.OK)
					.message("책 생성 성공")
					
					.build();

		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}
	
}
