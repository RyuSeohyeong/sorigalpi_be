package com.spring.sorigalpi.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public Book findByBookId(BookDTO bookDTO) { //동화책 id로 찾기
		
		UUID uuid = bookDTO.getBookId(); 

		Book test = bookRepository.findByBookId(uuid);
		
		return  test;
		
	}
	
	public List<BookDTO> findByBookName(String bookName){
		
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
	/*
	public String deleteBookById(BookDTO bookDTO) { //동화책 ID로 삭제
		
		String result;
		String bookId = bookDTO.getBookId();
		
		try {
			bookRepository.deleteById(bookId);
			result = "삭제 성공";
		} catch (Exception e) {
			e.printStackTrace();
			result = "삭제 실패 해당 동화책이 존재하지 않습니다.";
		}
		
		return result;
	}
	*/
	
}
