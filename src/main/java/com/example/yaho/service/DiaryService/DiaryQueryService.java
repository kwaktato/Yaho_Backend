package com.example.yaho.service.DiaryService;

import com.example.yaho.domain.Diary;
import com.example.yaho.web.dto.DiaryRequestDTO;

import java.time.LocalDate;

public interface DiaryQueryService {
    Diary getDiary(Long memberId, LocalDate date);

    Diary modifyDiary(Long memberId, DiaryRequestDTO.ModifyDto request);
}
