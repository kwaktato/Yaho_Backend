package com.example.yaho.web.dto;

import com.example.yaho.domain.Member;
import com.example.yaho.domain.enums.FavoriteClub;
import lombok.*;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
 public class LoginResponseDto {
    boolean loginSuccess;
    Long socialId;
    String accessToken;
}
