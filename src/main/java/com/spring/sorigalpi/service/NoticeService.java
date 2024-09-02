package com.spring.sorigalpi.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.spring.sorigalpi.base.Base;
import com.spring.sorigalpi.dto.NoticeDto;
import com.spring.sorigalpi.entity.Member;
import com.spring.sorigalpi.entity.Notice;
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
	
	public List<NoticeDto> listNotice(){ //공지사항 글 목록 조회
		
		List<Notice> noticeList = noticeRepository.findAll();
		List<NoticeDto> noticeDtoList = noticeList.stream().map(Notice::toDto).collect(Collectors.toList());

		return noticeDtoList;
}
	
	@Transactional
	public String updateNotice(NoticeDto noticeDto, String noticeId) { //공지사항 글 수정
	
	    String memberId = noticeDto.getMemberId();

		Member member = memberRepository.findById(memberId).orElse(null);
		
		if (member != null && "ROLE_ADMIN".equals(member.getRole())) {
		
		Notice notice = noticeRepository.findById(noticeId)
				.orElseThrow(() -> new OtherException(ErrorCode.NOTICE_NOT_FOUND));
				
			notice.updateNotice(noticeDto.getTitle(), noticeDto.getContent());
			
			return noticeId;
	}
		
		throw new OtherException(ErrorCode.NOTICE_UPDATE_IMPOSSIBLE);
	
	}
}