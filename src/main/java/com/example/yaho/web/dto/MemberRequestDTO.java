package com.example.yaho.web.dto;

import com.example.yaho.domain.enums.FavoriteClub;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

public class MemberRequestDTO {

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateMemberDTO {
        private String accessToken;
        private String nickname;
        private FavoriteClub favoriteClub;
        private MultipartFile profileImg;
    }
}