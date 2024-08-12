package com.example.yaho.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class DiaryRequestDTO {
    @Setter
    @Getter
    public static class WriteDto{
        @NotNull
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate date;

        @NotNull
        String emoticon;
        // mvp를 사용자가 선택??
        String mvp;

        String content;

        MultipartFile mvpPicture;

        String location;
    }

    @Getter
    public static class GetDto{
        @NotNull
        LocalDate date;
    }

}
