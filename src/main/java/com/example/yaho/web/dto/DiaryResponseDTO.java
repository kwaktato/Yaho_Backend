package com.example.yaho.web.dto;

import com.example.yaho.domain.enums.Location;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DiaryResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WriteResultDto{
        Long diaryId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmotionResultDTO{
        String emotionImageUrl;
        String FavoriteClubImageUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetResultDto{
        @NotNull
        String emotionImageUrl;
        Integer location;
        // mvp를 사용자가 선택??
        String mvp;
        String content;
        String mvpImageUrl;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModifyResultDto {
        Long diaryId;
        LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MvpImageResultDto{
        Long diaryId;
        LocalDateTime updatedAt;
    }

}