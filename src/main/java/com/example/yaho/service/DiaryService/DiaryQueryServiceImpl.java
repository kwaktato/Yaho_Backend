package com.example.yaho.service.DiaryService;

import com.example.yaho.domain.Diary;
import com.example.yaho.repository.DiaryRepository;
import com.example.yaho.web.dto.DiaryRequestDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryQueryServiceImpl implements DiaryQueryService{

    private final DiaryRepository diaryRepository;

    @Override
    @Transactional
    public Diary getDiary(DiaryRequestDTO.GetDto request) {

        return diaryRepository.findByDate(request.getDate());
    }
}
