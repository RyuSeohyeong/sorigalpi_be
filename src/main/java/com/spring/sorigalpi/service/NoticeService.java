package com.spring.sorigalpi.service;

import org.springframework.stereotype.Service;
import com.spring.sorigalpi.base.Base;
import com.spring.sorigalpi.dto.NoticeDto;
import com.spring.sorigalpi.entity.Member;
import com.spring.sorigalpi.exception.ErrorCode;
import com.spring.sorigalpi.exception.OtherException;
import com.spring.sorigalpi.repository.MemberRepository;
import com.spring.sorigalpi.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService extends Base {
	
	private final NoticeRepository noticeRepository;
	private final MemberRepository memberRepository;
	
	public String createNotice(NoticeDto noticeDto) {
		
		String memberId = noticeDto.getMemberId();
	
		
		Member member = memberRepository.findById(memberId).orElse(null);
		
		
		if (member != null && "ROLE_ADMIN".equals(member.getRole())) {
	
			noticeDto.setNoticeId(createRandomUuId());
		
			return noticeRepository.save(noticeDto.toEntity()).getNoticeId();
}
		
		throw new OtherException(ErrorCode.NO_AUTHORIZED);
}
}