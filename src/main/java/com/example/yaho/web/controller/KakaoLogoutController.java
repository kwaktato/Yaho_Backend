package com.example.yaho.web.controller;

import com.example.yaho.apiPayload.ApiResponse;
import com.example.yaho.service.KakaoService.KakaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth/callback")
public class KakaoLogoutController {
    private final KakaoService kakaoService;

    @Operation(summary = "카카오 로그아웃 API", description = "엑세스 토큰을 받아 카카오 로그아웃 처리")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "인가 코드를 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "인가 코드가 만료되었습니다.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "인가 코드가 유효하지 않습니다.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "accessToken", description = "엑세스 토큰입니다."),
    })
    @GetMapping("/logout/kakao")
    public ApiResponse<String> kakaoLogout(@RequestParam("accessToken") String accessToken) {

//        // 인가 코드를 사용해 카카오 Access Token을 획득
//        String accessToken = kakaoService.getKakaoAccessToken(code).getAccess_token();

        // 카카오 로그아웃 서비스 호출
        kakaoService.kakaoLogout(accessToken);
        return ApiResponse.onSuccess("카카오 로그아웃 성공");
    }
}
