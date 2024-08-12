package com.example.yaho.converter;

import com.example.yaho.domain.Diary;
import com.example.yaho.domain.Game;
import com.example.yaho.web.dto.DiaryRequestDTO;
import com.example.yaho.web.dto.DiaryResponseDTO;

import java.time.LocalDateTime;

public class DiaryConverter {

    public static DiaryResponseDTO.WriteResultDto toWriteResultDTO(Diary diary) {
        return DiaryResponseDTO.WriteResultDto.builder()
                .diaryId(diary.getId())
                .createdAt(diary.getCreatedAt()) // Use the createdAt from the Diary object
                .build();
    }

    public static Diary toDiary(DiaryRequestDTO.WriteDto request, Game game) {
        LocalDateTime now = LocalDateTime.now();

        return Diary.builder()
                .date(request.getDate())
                .emotionImageUrl(request.getEmoticon())
                .mvp(request.getMvp())  // 수정 가능성
                .content(request.getContent())
                .game(game) // Set the Game entity
                .createdAt(now) // Set the created timestamp
                .updatedAt(now) // Set the updated timestamp
                .build();
    }

    public static DiaryResponseDTO.EmotionResultDTO toEmotionResultDTO(String emotionImageUrl, String favoriteClubImageUrl){

        return DiaryResponseDTO.EmotionResultDTO.builder()
                .emotionImageUrl(emotionImageUrl)
                .FavoriteClubImageUrl(favoriteClubImageUrl)
                .build();
    }
}
