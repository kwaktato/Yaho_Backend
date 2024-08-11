package com.example.yaho.web.controller;

import com.example.yaho.apiPayload.ApiResponse;
import com.example.yaho.service.MemberService.MemberService;
import com.example.yaho.utils.SecurityUtil;
import com.example.yaho.web.dto.MemberResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    @Operation(summary = "멤버 조회 API",
            description = "사용자의 닉네임과 프로필 이미지를 조회하는 API입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content),
    })
    public ApiResponse<MemberResponseDTO.memberProfileDTO> getMemberProfile() {

        String memberName = SecurityUtil.getCurrentUserName();
        MemberResponseDTO.memberProfileDTO response = memberService.getMemberProfile(memberName);
        return ApiResponse.onSuccess(response);
    }
}
