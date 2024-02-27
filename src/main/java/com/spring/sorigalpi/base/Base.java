package com.spring.sorigalpi.base;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass // JPA Entity 클래스들이 BaseTime Entity를 상속할 경우 필드들도 column으로 자동인식 되도록 함
@EntityListeners(AuditingEntityListener.class) // Entity의 변화를 감지하고 Table의 데이터를 조작하는 일을 함
public class Base { //중복되는 메소드들
	
	public String createRandomUuId() {	//UUID 생성
		
		String resultUuid = UUID.randomUUID().toString();
		
		return resultUuid;
	}
	
	public UUID createUUID() {
		return UUID.randomUUID();
	}
	
	@CreatedDate //Entity가 생성되어 저장될 때 시간 저장
	private LocalDateTime creDate;
	
	@LastModifiedDate //Entity가 변경되어 저장될 때 시간 저장
	private LocalDateTime modifiedDate;
	
}
