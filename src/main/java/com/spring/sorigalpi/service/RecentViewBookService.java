package com.spring.sorigalpi.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.sorigalpi.dto.RecentViewBookDTO;
import com.spring.sorigalpi.entity.RecentViewBook;
import com.spring.sorigalpi.repository.RecentViewBookRepository;

@Service("recentViewBookService")
public class RecentViewBookService {
	
	@Autowired
	private RecentViewBookRepository recentViewBookRepository;
	
	public List<RecentViewBookDTO> getAllRecentViewBookList() { // 최근 본 동화 테이블 모든 정보 가져오기
		
		List<RecentViewBook> entityList = recentViewBookRepository.findAll();
		
		List<RecentViewBookDTO> dtoList = entityList.stream().map(RecentViewBook::toDTO).collect(Collectors.toList());
		
		return dtoList;
	}

	
	public RecentViewBookDTO createReadRecentBook(RecentViewBookDTO dto) { //최근 본 동화 저장
		
		RecentViewBook entity = recentViewBookRepository.save(dto.toEntity());
		RecentViewBookDTO RVBdto = entity.toDTO();
		
		return RVBdto;
		
	}
	
	public void deleteBookById(RecentViewBookDTO dto) { //동화책 ID로 삭제
		
		UUID bookId = dto.getBookId();
		RecentViewBook bookInfo = recentViewBookRepository.findByBookId(bookId);
		
		if (bookInfo != null) {
			recentViewBookRepository.delete(bookInfo);
		}else {
			
		}
		
		
	}
}
