package com.spring.sorigalpi.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.sorigalpi.base.Base;
import com.spring.sorigalpi.dto.BookDTO;
import com.spring.sorigalpi.entity.Book;
import com.spring.sorigalpi.repository.BookRepository;


@Service("bookService")
public class BookService extends Base{
	//@Autowired
	//private BookDTO bookDTO;
	@Autowired
	private BookRepository bookRepository;

	public List<BookDTO> getAllBook(){ //동화책 테이블 모든 정보 가져오기
		
		List<Book> bookList = bookRepository.findAll();
		
		List<BookDTO> bookDTOList = bookList.stream().map(Book::toDTO).collect(Collectors.toList()); //List<Book> -> List<BookDTO>
		
		return bookDTOList;
	}
	
	public BookDTO findByBookId(BookDTO bookDTO) { //동화책 id로 찾기
		
		UUID bookId = bookDTO.getBookId(); 

		BookDTO bookInfo = bookRepository.findByBookId(bookId).toDTO();
		
		return  bookInfo;
		
	}
	
	public List<BookDTO> findByBookName(String bookName){ //동화책 이름으로 찾기
		
		List<Book> bookList = bookRepository.findAllBybookName(bookName);
		
		List<BookDTO> bookDTOList = bookList.stream().map(Book::toDTO).collect(Collectors.toList()); //List<Book> -> List<BookDTO>
		
		return bookDTOList; 
		
		
	}
	
	public String createBook(BookDTO bookDTO) { //동화책 생성
		
		bookDTO.setBookId(createUUID());
		
		if(bookDTO.getBlind() == null) {
			bookDTO.setBlind("NO");
		}
		
		return bookRepository.save(bookDTO.toEntity()).getBookId().toString();
	}
	
	public String deleteBookById(String memberId, BookDTO bookDTO) { //동화책 ID로 삭제
		
		String result;
		UUID bookId = bookDTO.getBookId();
		Book bookInfo = bookRepository.findByBookId(bookId);
		
		String checkMemberId = bookInfo.getMemberId();
		
		if (checkMemberId == memberId) {
			bookRepository.delete(bookInfo);
			result = "삭제 성공";
		}else {
			result = "삭제 불가 사용자 정보 다름";
		}
		return result;
	}
	
	@Transactional
	public String updateBook(String memberId, BookDTO bookDTO) {
		Book bookInfo = bookRepository.findByBookId(bookDTO.getBookId());
		if (bookInfo != null) {
			String checkMemberId = bookInfo.getMemberId();
			
			if(checkMemberId.equals(memberId)) {
				bookInfo.updateBook(bookDTO.getBookName(), bookDTO.getPageNum(), bookDTO.getStatus(), bookDTO.getBlind(), bookDTO.getRecordable(), bookDTO.getInfo());
				return "수정 완료";
			}else {
				return "사용자 정보 불일치";
			}
		}else {
			return "해당하는 책 정보 없음";
		}																			
	}
	

}
