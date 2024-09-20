package com.spring.sorigalpi.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


import com.spring.sorigalpi.entity.Book;

public interface BookRepository extends JpaRepository<Book, String>{
	
	
	Optional<Book> findByBookId(UUID bookId);
	public List<Book> findAllBybookNameOrderByCreDateDesc(String bookName);
	public List<Book> findAllBybookNameOrderByCreDateAsc(String bookName);
	public List<Book> findAllByMemberId(String memberId);
}
