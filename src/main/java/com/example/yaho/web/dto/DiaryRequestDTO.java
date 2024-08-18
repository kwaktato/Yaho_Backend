package com.example.yaho.web.dto;

import com.example.yaho.validation.annotation.CheckEmotion;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
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

        @NotNull
        Integer location;
    }

    @Getter
    @Setter
    public static class GetDto{
        @NotNull
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate date;
    }


    @Getter
    @Setter
    public static class EmotionDto{

        @NotNull
        @CheckEmotion
        Integer emotionImage;
    }


    @Setter
    @Getter
    public static class ModifyDto {
        @NotNull
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate date;
        @NotNull
        String emoticon;
        // mvp를 사용자가 선택??
        String mvp;
        String content;

        MultipartFile mvpPicture;
        @NotNull
        Integer location;
    }

}
