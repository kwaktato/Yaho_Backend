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


    public void setMember(Member member) {
        this.member = member;
    }

}
