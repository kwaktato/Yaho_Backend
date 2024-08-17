package com.example.yaho.service.DiaryService;


import com.example.yaho.web.dto.DiaryRequestDTO;
import com.example.yaho.domain.Diary;

public interface DiaryQueryService {


    String getEmotionImageUrl(DiaryRequestDTO.EmotionDto request);

    String getFavoriteClubImageUrl(Long memberId);
  
   Diary getDiary(DiaryRequestDTO.GetDto request);

}
