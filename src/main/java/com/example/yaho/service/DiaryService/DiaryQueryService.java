package com.example.yaho.service.DiaryService;

import com.example.yaho.domain.Diary;
import com.example.yaho.web.dto.DiaryRequestDTO;

public interface DiaryQueryService {
    Diary getDiary(DiaryRequestDTO.GetDto request);
}
