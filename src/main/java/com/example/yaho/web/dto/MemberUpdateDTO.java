package com.example.yaho.web.dto;

import com.example.yaho.domain.enums.FavoriteClub;
import com.example.yaho.domain.enums.FavoriteTeam;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateDTO {

    private String nickname;
    private FavoriteClub favoriteClub;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class profileImg {
        private MultipartFile profileImg;
    }
}
