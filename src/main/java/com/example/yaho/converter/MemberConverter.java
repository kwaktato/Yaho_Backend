package com.example.yaho.converter;

import com.example.yaho.domain.Member;
import com.example.yaho.web.dto.MemberResponseDTO;

public class MemberConverter {

    public static MemberResponseDTO.memberProfileDTO toProfileDTO(Member member){

        return MemberResponseDTO.memberProfileDTO.builder()
                .nickname(member.getNickname())
                .favoriteTeam(member.getFavoriteTeam())
                .build();
    }
}
