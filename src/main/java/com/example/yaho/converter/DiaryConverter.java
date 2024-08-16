package com.example.yaho.converter;

import com.example.yaho.domain.Diary;
import com.example.yaho.domain.Game;
import com.example.yaho.domain.Member;
import com.example.yaho.domain.enums.Location;
import com.example.yaho.web.dto.DiaryRequestDTO;
import com.example.yaho.web.dto.DiaryResponseDTO;

import java.time.LocalDateTime;

public class DiaryConverter {

    public static DiaryResponseDTO.WriteResultDto toWriteResultDTO(Diary diary) {
        return DiaryResponseDTO.WriteResultDto.builder()
                .diaryId(diary.getId())
                .createdAt(diary.getCreatedAt())
                .build();
    }

    public static Diary toDiary(DiaryRequestDTO.WriteDto request, Game game, Member member) {
        LocalDateTime now = LocalDateTime.now();

        Location location = getLocationFromRequest(request.getLocation());

        return Diary.builder()
                .member(member)
                .date(request.getDate())
                .location(location)
                .emoticon(request.getEmoticon())
                .mvp(request.getMvp())  // 수정 가능성
                .content(request.getContent())
                .game(game)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public static DiaryResponseDTO.GetResultDto toGetResultDTO(Diary diary) {
        return DiaryResponseDTO.GetResultDto.builder()
                .mvp(diary.getMvp())
                .mvpImageUrl(diary.getMvpImageUrl())
                .location(getLocationCode(diary.getLocation()))
                .content(diary.getContent())
                .emoticon(diary.getEmoticon())
                .build();
    }

    public static DiaryResponseDTO.ModifyResultDto toModifyResultDTO(Diary diary) {
        return DiaryResponseDTO.ModifyResultDto.builder()
                .diaryId(diary.getId())
                .updatedAt(diary.getUpdatedAt())
                .build();
    }

    public static Diary modifyDiary(DiaryRequestDTO.ModifyDto request, Diary diary) {
        LocalDateTime now = LocalDateTime.now();
        diary.setContent(request.getContent());
        diary.setMvp(request.getMvp());
        diary.setEmoticon(request.getEmoticon());
        diary.setUpdatedAt(now);
        diary.setLocation(getLocationFromRequest(request.getLocation()));
        diary.setDate(request.getDate());

        return diary;
    }

    private static Location getLocationFromRequest(int locationCode) {
        switch (locationCode) {
            case 1:
                return Location.GOCHEOK_SKY_DOME;
            case 2:
                return Location.KIA_CHAMPIONS_FIELD;
            case 3:
                return Location.DAEGU_SAMSUNG_LIONS_PARK;
            case 4:
                return Location.SAJIK_BASEBALL_STADIUM;
            case 5:
                return Location.SUWON_KT_WIZ_PARK;
            case 6:
                return Location.INCHEON_SSG_LANDERS_FIELD;
            case 7:
                return Location.JAMSIL_BASEBALL_STADIUM;
            case 8:
                return Location.CHANGWON_NC_PARK;
            default:
                throw new IllegalArgumentException("Invalid location code: " + locationCode);
        }
    }

    private static int getLocationCode(Location location) {
        if (location == null) {
            return 0; // 또는 null을 반환하거나 예외를 던질 수 있습니다.
        }

        switch (location) {
            case GOCHEOK_SKY_DOME:
                return 1;
            case KIA_CHAMPIONS_FIELD:
                return 2;
            case DAEGU_SAMSUNG_LIONS_PARK:
                return 3;
            case SAJIK_BASEBALL_STADIUM:
                return 4;
            case SUWON_KT_WIZ_PARK:
                return 5;
            case INCHEON_SSG_LANDERS_FIELD:
                return 6;
            case JAMSIL_BASEBALL_STADIUM:
                return 7;
            case CHANGWON_NC_PARK:
                return 8;
            default:
                throw new IllegalArgumentException("Unknown location: " + location);
        }
    }
}
