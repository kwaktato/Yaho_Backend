package com.example.yaho.converter;

import com.example.yaho.domain.Diary;
import com.example.yaho.domain.Member;
import com.example.yaho.web.dto.MemberResponseDTO;

import java.time.LocalDate;

public class MemberConverter {

    public static MemberResponseDTO.memberProfileDTO toProfileDTO(Member member){

        return MemberResponseDTO.memberProfileDTO.builder()
                .nickname(member.getNickname())
                .favoriteClub(member.getFavoriteClub())
                .build();
    }

    public static MemberResponseDTO.mypageDiaryDTO toDiaryDTO(Diary diary) {

        return MemberResponseDTO.mypageDiaryDTO.builder()
                .emotionImageUrl(diary.getEmotionImageUrl())
                .content(diary.getContent())
                .location(diary.getLocation())
                .date(diary.getDate())
                .mvp(diary.getMvp())
                .build();
    }
}
