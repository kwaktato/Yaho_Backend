package com.example.yaho.web.controller;

import com.example.yaho.service.KakaoService.KakaoService;
import com.example.yaho.web.dto.KakaoTokenDto;
import com.example.yaho.web.dto.LoginResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import com.example.yaho.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth/callback")
public class KakaoLoginController {

    private final KakaoService kakaoService;

    @Operation(summary = "카카오 로그인 API",description = "인가 코드를 받고 토큰을 리턴하는 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "code", description = "인가 코드 입니다."),
    })
    @GetMapping("/kakao")
    public ApiResponse<LoginResponseDto> callback(@RequestParam("code") String code) {

        String accessToken = kakaoService.getKakaoAccessToken(code).getAccess_token();
        LoginResponseDto loginResponseDto = kakaoService.kakaoLogin(accessToken);
        
        return ApiResponse.onSuccess(loginResponseDto);
    }

    @Operation(summary = "카카오 회원탈퇴 API", description = "토큰을 받아 카카오 회원탈퇴 및 서비스 회원탈퇴를 처리")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "인가 코드를 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "인가 코드가 만료되었습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "인가 코드가 유효하지 않습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @PostMapping("/kakao/unlink")
    public ApiResponse<Void> unlinkAndDeleteKakaoAccount(@RequestHeader("Authorization") String accessToken, @RequestParam Long socialId) {
        try {
            log.info("카카오 회원탈퇴 요청 - socialId: {}", socialId);
            kakaoService.kakaoUnlinkAndDeleteMember(accessToken, socialId);
            return ApiResponse.onSuccess(null);
        } catch (Exception e) {
            log.error("카카오 회원탈퇴 처리 중 오류 발생", e);
            return ApiResponse.onFailure("AUTH006", "회원탈퇴 처리 중 오류가 발생했습니다.", null);
        }
    }
}
