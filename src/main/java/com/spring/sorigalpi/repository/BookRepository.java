package com.spring.sorigalpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.sorigalpi.entity.Book;

public interface BookRepository extends JpaRepository<Book, String>{
	
	
	public Book findByBookId(String bookId);
}
