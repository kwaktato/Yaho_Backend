package com.example.yaho.converter;

import com.example.yaho.domain.Diary;
import com.example.yaho.domain.Member;
import com.example.yaho.web.dto.MemberRequestDTO;
import com.example.yaho.web.dto.MemberResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MemberConverter {

    public static Member toMember(MemberRequestDTO.CreateMemberDTO request, String profileImg) {
        LocalDateTime now = LocalDateTime.now();

        return Member.builder()
                .nickname(request.getNickname())
                .favoriteClub(request.getFavoriteClub())
                .profileImage(profileImg)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public static MemberResponseDTO.memberDTO toMemberDTO(Member member) {
        return MemberResponseDTO.memberDTO.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .favoriteClub(member.getFavoriteClub())
                .profileImgURL(member.getProfileImage())
                .build();
    }

    public static MemberResponseDTO.memberProfileDTO toProfileDTO(Member member){

        return MemberResponseDTO.memberProfileDTO.builder()
                .nickname(member.getNickname())
                .favoriteClub(member.getFavoriteClub())
                .updatedAt(member.getUpdatedAt())
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
