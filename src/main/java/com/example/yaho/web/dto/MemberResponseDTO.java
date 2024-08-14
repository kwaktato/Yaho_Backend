package com.example.yaho.web.dto;

import com.example.yaho.domain.enums.FavoriteTeam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
