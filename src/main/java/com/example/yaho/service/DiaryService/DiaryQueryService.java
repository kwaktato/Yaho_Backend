package com.example.yaho.service.DiaryService;

import com.example.yaho.domain.Diary;
import com.example.yaho.web.dto.DiaryRequestDTO;

import java.time.LocalDate;

public interface DiaryQueryService {
    Diary getDiary(LocalDate date);

    Diary modifyDiary(DiaryRequestDTO.ModifyDto request);
}
