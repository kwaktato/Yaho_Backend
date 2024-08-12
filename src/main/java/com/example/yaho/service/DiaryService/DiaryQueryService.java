package com.example.yaho.service.DiaryService;

import com.example.yaho.web.dto.DiaryRequestDTO;

public interface DiaryQueryService {


    String getEmotionImageUrl(DiaryRequestDTO.EmotionDto request);

    String getFavoriteClubImageUrl(Long memberId);
}
