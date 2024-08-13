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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
    @PostMapping(value = "/write", consumes = "multipart/form-data")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content),
    })
    public ApiResponse<DiaryResponseDTO.WriteResultDto> write(
            @ModelAttribute @Valid DiaryRequestDTO.WriteDto request) {
        // 서비스 호출하여 일기 저장
        Diary diary = diaryCommandService.writeDiary(request);
        return ApiResponse.onSuccess(DiaryConverter.toWriteResultDTO(diary));
    }


    @Operation(summary = "일기 확인 API", description = "작성한 일기를 확인하는 API입니다.")
    @GetMapping("/get")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content),
    })
    public ApiResponse<DiaryResponseDTO.GetResultDto> get(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Diary diary = diaryQueryService.getDiary(date);
        return ApiResponse.onSuccess(DiaryConverter.toGetResultDTO(diary));
    }



    @Operation(summary = "일기 수정 API", description = "작성한 일기를 수정하는 API입니다.")
    @PatchMapping(value = "/modify", consumes = "multipart/form-data")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content),
    })

    public ApiResponse<DiaryResponseDTO.ModifyResultDto> modify(
            @ModelAttribute @Valid DiaryRequestDTO.ModifyDto request) {
        // 서비스 호출하여 일기 저장
        Diary diary = diaryQueryService.modifyDiary(request);
        return ApiResponse.onSuccess(DiaryConverter.toModifyResultDTO(diary));
    }
}
