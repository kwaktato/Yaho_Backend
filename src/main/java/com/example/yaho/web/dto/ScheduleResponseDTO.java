package com.example.yaho.web.dto;

import com.example.yaho.domain.enums.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class ScheduleResponseDTO {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScheduleDto {
        Long gameId;
        LocalDate date;
        String team1;
        String team2;
        Location location;
        String time;
    }
}
