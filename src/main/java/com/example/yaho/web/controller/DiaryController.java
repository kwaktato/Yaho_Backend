package com.example.yaho.web.controller;

import com.example.yaho.apiPayload.ApiResponse;
import com.example.yaho.converter.DiaryConverter;
import com.example.yaho.domain.Diary;
import com.example.yaho.service.DiaryService.DiaryCommandService;
import com.example.yaho.service.DiaryService.DiaryQueryService;
import com.example.yaho.web.dto.DiaryRequestDTO;
import com.example.yaho.web.dto.DiaryResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/diarys")
public class DiaryController {

    private final DiaryCommandService diaryCommandService;
    private final DiaryQueryService diaryQueryService;

    // Constructor-based injection
    @Autowired
    public DiaryController(DiaryCommandService diaryCommandService, DiaryQueryService diaryQueryService) {
        this.diaryCommandService = diaryCommandService;
        this.diaryQueryService = diaryQueryService;
    }

    @Operation(summary = "일기 쓰기 API", description = "작성한 일기를 저장하는 API입니다.")
    @PostMapping("/{gameId}/write")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content),
    })
    @Parameters({
            @Parameter(name = "gameId", description = "경기의 아이디, path variable 입니다!")
    })
    public ApiResponse<DiaryResponseDTO.WriteResultDto> write(
            @PathVariable(name = "gameId") Long gameId,
            @RequestBody @Valid DiaryRequestDTO.WriteDto request) {
        // 서비스 호출하여 일기 저장
        Diary diary = diaryCommandService.writeDiary(gameId, request);
        return ApiResponse.onSuccess(DiaryConverter.toWriteResultDTO(diary));
    }


    @Operation(summary = "일기 확인 API", description = "작성한 일기를 확인하는 API입니다.")
    @PostMapping("/get")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content),
    })

    public ApiResponse<DiaryResponseDTO.GetResultDto> write(
            @RequestBody @Valid DiaryRequestDTO.GetDto request
    ) {
        // 서비스 호출하여 일기 저장
        Diary diary = diaryQueryService.getDiary(request);
        return ApiResponse.onSuccess(DiaryConverter.toGetResultDTO(diary));
    }
}
