package com.example.yaho.converter;

import com.example.yaho.domain.Game;
import com.example.yaho.web.dto.ScheduleResponseDTO;

import java.time.format.DateTimeFormatter;

public class ScheduleConverter {

    public static ScheduleResponseDTO.ScheduleDto toScheduleDto(Game game) {
        // 날짜 형식 변환을 위한 포맷팅
        /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일");
        String formattedDate = game.getDate().format(formatter);*/

        return ScheduleResponseDTO.ScheduleDto.builder()
                .gameId(game.getId())
                .date(game.getDate())
                .team1(game.getTeam1())
                .team2(game.getTeam2())
                .location(game.getLocation())
                .build();
    }
}