package com.example.yaho.converter;

import com.example.yaho.domain.Diary;
import com.example.yaho.domain.Member;
import com.example.yaho.web.dto.MemberResponseDTO;

import java.time.LocalDate;

public class MemberConverter {

    public static MemberResponseDTO.memberProfileDTO toProfileDTO(Member member){

        return MemberResponseDTO.memberProfileDTO.builder()
                .nickname(member.getNickname())
                .favoriteTeam(member.getFavoriteTeam())
                .build();
    }

    public static MemberResponseDTO.mypageDiaryDTO toDiaryDTO(Diary diary) {

        return MemberResponseDTO.mypageDiaryDTO.builder()
                .emotion(diary.getEmoticon())
                .content(diary.getContent())
                .location(diary.getLocation())
                .date(diary.getDate())
                .mvp(diary.getMvp())
                .build();
    }
}
