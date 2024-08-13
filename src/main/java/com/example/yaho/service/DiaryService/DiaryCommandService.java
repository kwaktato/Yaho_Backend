package com.example.yaho.service.DiaryService;

import com.example.yaho.domain.Diary;
import com.example.yaho.web.dto.DiaryRequestDTO;
import jakarta.transaction.Transactional;

public interface DiaryCommandService {

    @Transactional
    Diary writeDiary(Long memberId, DiaryRequestDTO.WriteDto request);

}
