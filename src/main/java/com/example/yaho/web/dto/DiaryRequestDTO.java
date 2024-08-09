package com.example.yaho.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

public class DiaryRequestDTO {
    @Getter
    public static class WriteDto{
        @NotNull
        LocalDate date;
        @NotNull
        String emoticon;
        // mvp를 사용자가 선택??
        String mvp;
        String content;
        String imageUrl;
        // location 추가
        String location;
    }

    @Getter
    public static class GetDto{
        @NotNull
        LocalDate date;
    }

}
