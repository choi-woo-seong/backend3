package com.project.msy.notice.service;

import com.project.msy.notice.dto.NoticeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoticeService {
    // 공지 등록
    NoticeDto createNotice(NoticeDto noticeDto);

    // 전체 공지사항 페이징 조회
    Page<NoticeDto> getAllNotices(Pageable pageable);

    // 단일 공지 조회 (조회수 증가 포함)
    NoticeDto getNoticeById(Long id);

    // 공지 삭제
    void deleteNotice(Long id);


}
