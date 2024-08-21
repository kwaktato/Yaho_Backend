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

    @Operation(summary = "카카오 연결 끊기 API", description = "토큰을 받아 카카오 연결 끊기 처리")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "인가 코드를 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "인가 코드가 만료되었습니다.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "인가 코드가 유효하지 않습니다.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    /*@Parameters({
            @Parameter(name = "accessToken", description = "엑세스 토큰입니다."),
    })*/
    /*@GetMapping("/unlink/kakao")
    public ApiResponse<String> kakaoUnlink(@RequestParam("accessToken") String accessToken) {

        // 카카오 로그아웃 서비스 호출
        kakaoService.kakaoUnlink(accessToken);
        return ApiResponse.onSuccess("카카오 회원탈퇴 성공");
    }*/
    @PostMapping("/kakao/unlink")
    public ApiResponse<Void> unlinkKakaoAccount(@RequestHeader("Authorization") String accessToken) {
        kakaoService.kakaoUnlink(accessToken);
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "야호 회원탈퇴 API", description = "토큰을 받아 회원탈퇴 처리")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "인가 코드를 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "인가 코드가 만료되었습니다.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "인가 코드가 유효하지 않습니다.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @DeleteMapping("/kakao/delete")
    public ApiResponse<Void> unlinkAndDeleteKakaoAccount(@RequestHeader("Authorization") String accessToken, @RequestParam Long memberId) {
        kakaoService.kakaoUnlinkAndDeleteMember(accessToken, memberId);
        return ApiResponse.onSuccess(null);
    }


}




