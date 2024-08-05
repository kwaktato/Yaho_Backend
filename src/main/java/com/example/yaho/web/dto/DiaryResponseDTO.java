package com.example.yaho.web.dto;

import lombok.*;

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
}