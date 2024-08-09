package com.example.yaho.web.controller;

import com.example.yaho.apiPayload.ApiResponse;
import com.example.yaho.converter.ScheduleConverter;
import com.example.yaho.domain.Game;
import com.example.yaho.service.ScheduleService.ScheduleCommandService;
import com.example.yaho.web.dto.ScheduleResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleCommandService scheduleCommandService;

    @Autowired
    public ScheduleController(ScheduleCommandService scheduleCommandService) {
        this.scheduleCommandService = scheduleCommandService;
    }

    @Operation(summary = "오늘의 경기 일정 API", description = "오늘 날짜의 경기를 반환하는 API입니다.")
    @GetMapping("/today")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SCHEDULE404", description = "오늘 날짜의 경기가 없습니다.", content = @Content)
    })
    public ApiResponse<List<ScheduleResponseDTO.ScheduleDto>> getTodaySchedules() {
        List<Game> todayGames = scheduleCommandService.getTodayGames();
        List<ScheduleResponseDTO.ScheduleDto> result = todayGames.stream()
                .map(ScheduleConverter::toScheduleDto)
                .collect(Collectors.toList());
        return ApiResponse.onSuccess(result);
    }
}