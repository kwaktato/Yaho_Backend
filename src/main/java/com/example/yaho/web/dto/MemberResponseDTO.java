package com.example.yaho.web.dto;

import com.example.yaho.domain.enums.FavoriteClub;
import com.example.yaho.domain.enums.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MemberResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class memberDTO {
        private Long id;
        private String nickname;
        private FavoriteClub favoriteClub;
        private String profileImgURL;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class memberProfileDTO {
        private String nickname;
        private FavoriteClub favoriteClub;
        private LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileImgDTO{
        private String profileImgURL;
        private LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class mypageDiaryDTO { // 마이 페이지 중 일기 내용 DTO
        String emotionImageUrl;
        String content;
        Location location;
        LocalDate date;
        String mvp;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class mypageDiaryListDTO {
        private List<mypageDiaryDTO> diaryList;
    }
}
