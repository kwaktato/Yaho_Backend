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
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StringMultipartFileEditor;

import java.time.LocalDate;

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

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 빈 문자열을 null로 변환
        binder.registerCustomEditor(MultipartFile.class, new StringMultipartFileEditor());
    }

    // Constructor-based injection
    @Autowired
    public DiaryController(DiaryCommandService diaryCommandService, DiaryQueryService diaryQueryService) {
        this.diaryCommandService = diaryCommandService;
        this.diaryQueryService = diaryQueryService;
    }


    @Operation(summary = "일기 쓰기 API", description = "작성한 일기를 저장하는 API입니다.")
    @PostMapping(value = "/{memberId}/write", consumes = "multipart/form-data")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "사용자의 아이디, path variable 입니다!"),
            @Parameter(name = "location", description = "일기 작성 위치 코드입니다. Figma 기준 순서대로 1~8이며, 1이 고척 스카이돔, 8이 창원 NC파크입니다.")
    })
    public ApiResponse<DiaryResponseDTO.WriteResultDto> write(
            @PathVariable(name = "memberId") @NotNull Long memberId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") @NotNull LocalDate date,
            @RequestParam("emoticon") @NotNull String emoticon,
            @RequestParam(value = "mvp", required = false) String mvp,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "mvpPicture", required = false) MultipartFile mvpPicture,
            @RequestParam("location") @NotNull Integer location) {

        DiaryRequestDTO.WriteDto request = new DiaryRequestDTO.WriteDto();
        request.setDate(date);
        request.setEmoticon(emoticon);
        request.setMvp(mvp);
        request.setContent(content);
        request.setMvpPicture(mvpPicture); // MultipartFile 처리
        request.setLocation(location);

        // 빈 파일이 전달된 경우, null로 처리
        if (request.getMvpPicture() != null && request.getMvpPicture().isEmpty()) {
            request.setMvpPicture(null);
        }

        Diary diary = diaryCommandService.writeDiary(memberId, request);
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


    @Operation(summary = "일기 확인 API", description = "작성한 일기를 확인하는 API입니다.")
    @GetMapping("/{memberId}/get")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content),
    })

    @Parameters({
            @Parameter(name = "memberId", description = "사용자의 아이디, path variable 입니다!")
    })
    public ApiResponse<DiaryResponseDTO.GetResultDto> get(
            @PathVariable(name = "memberId") Long memberId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Diary diary = diaryQueryService.getDiary(memberId, date);
        return ApiResponse.onSuccess(DiaryConverter.toGetResultDTO(diary));
    }

    @Operation(summary = "일기 수정 API", description = "작성한 일기를 수정하는 API입니다.")
    @PatchMapping(value = "/{memberId}/modify", consumes = "multipart/form-data")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "사용자의 아이디, path variable 입니다!"),
            @Parameter(name = "location", description = "일기 작성 위치 코드입니다. Figma 기준 순서대로 1~8이며, 1이 고척 스카이돔, 8이 창원 NC파크입니다.")
    })
    public ApiResponse<DiaryResponseDTO.ModifyResultDto> modify(
            @PathVariable(name = "memberId") @NotNull Long memberId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") @NotNull LocalDate date,
            @RequestParam("emoticon") @NotNull String emoticon,
            @RequestParam(value = "mvp", required = false) String mvp,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "mvpPicture", required = false) MultipartFile mvpPicture,
            @RequestParam("location") @NotNull Integer location) {

        DiaryRequestDTO.ModifyDto request = new DiaryRequestDTO.ModifyDto();
        request.setDate(date);
        request.setEmoticon(emoticon);
        request.setMvp(mvp);
        request.setContent(content);
        request.setMvpPicture(mvpPicture); // MultipartFile 처리
        request.setLocation(location);

        // 빈 파일이 전달된 경우, null로 처리
        if (request.getMvpPicture() != null && request.getMvpPicture().isEmpty()) {
            request.setMvpPicture(null);
        }

        // 서비스 호출하여 일기 저장
        Diary diary = diaryQueryService.modifyDiary(memberId, request);
        return ApiResponse.onSuccess(DiaryConverter.toModifyResultDTO(diary));
    }

}
