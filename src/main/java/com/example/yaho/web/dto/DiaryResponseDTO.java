package com.example.yaho.web.dto;

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
    public static class GetResultDto{
        @NotNull
        String emoticon;
        String location;
        // mvp를 사용자가 선택??
        String mvp;
        String content;
        String imageUrl;
    }
}