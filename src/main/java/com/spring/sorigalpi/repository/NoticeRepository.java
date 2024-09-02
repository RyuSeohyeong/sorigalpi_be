package com.spring.sorigalpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.sorigalpi.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, String> {

}
