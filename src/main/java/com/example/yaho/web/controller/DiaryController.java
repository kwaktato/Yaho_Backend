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
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/diarys")
public class DiaryController {

    private final DiaryCommandService diaryCommandService;
    private final DiaryQueryService diaryQueryService;

//    // Constructor-based injection
//    @Autowired
//    public DiaryController(DiaryCommandService diaryCommandService) {
//        this.diaryCommandService = diaryCommandService;
//    }

    @Operation(summary = "일기 쓰기 API", description = "작성한 일기를 저장하는 API입니다.")
    @PostMapping(value = "/{gameId}/write", consumes = "multipart/form-data")
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
            @ModelAttribute @Valid DiaryRequestDTO.WriteDto request) {
        // 서비스 호출하여 일기 저장
        Diary diary = diaryCommandService.writeDiary(gameId, request);
        return ApiResponse.onSuccess(DiaryConverter.toWriteResultDTO(diary));
    }

    @PostMapping("/{memberId}/emotion")
    @Operation(summary = "특정 멤버의 이모티콘 이미지, 구단 로고 조회 API",description = "이모티콘은 1~9 까지의 숫자를 입력하면 해당 이미지가 Response 되도록 구현했습니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<DiaryResponseDTO.EmotionResultDTO> getEmotionImage(
            @PathVariable(name = "memberId") Long memberId,
            @RequestBody @Valid DiaryRequestDTO.EmotionDto request)
    {
        String emotionImageUrl = diaryQueryService.getEmotionImageUrl(request);
        String favoriteClubImageUrl = diaryQueryService.getFavoriteClubImageUrl(memberId);
        return ApiResponse.onSuccess(DiaryConverter.toEmotionResultDTO(emotionImageUrl, favoriteClubImageUrl));
    }


}
