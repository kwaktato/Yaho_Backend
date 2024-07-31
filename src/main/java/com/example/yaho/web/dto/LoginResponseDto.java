package com.example.yaho.web.dto;

import com.example.yaho.domain.Member;
import lombok.*;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
 public class LoginResponseDto {
    boolean loginSuccess;
    Member member;
    AuthTokens token;


    public void setMembr(Member member) {
        this.member = member;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthTokens {
        String accessToken;
        String refreshToken;
        String grantType;
        Long expiresIn;


    }
}
