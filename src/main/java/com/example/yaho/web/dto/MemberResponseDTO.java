package com.example.yaho.web.dto;

import com.example.yaho.domain.enums.FavoriteTeam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class MemberResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class memberProfileDTO {
        private String nickname;
        private String profileImgUrl;
        private FavoriteTeam favoriteTeam;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileImgDTO{
        private Long memberId;
        private String profileImgURL;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class mypageDiaryDTO { // 마이 페이지 중 일기 내용 DTO
        String emotion;
        String content;
        String location;
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
