package com.example.yaho.web.controller;

import com.example.yaho.apiPayload.ApiResponse;
import com.example.yaho.domain.Member;
import com.example.yaho.repository.MemberRepository;
import com.example.yaho.service.MemberService.MemberService;
//import com.example.yaho.utils.SecurityUtil;
import com.example.yaho.web.dto.MemberResponseDTO;
import com.example.yaho.web.dto.MemberUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.apache.tomcat.util.http.fileupload.FileUploadBase.MULTIPART_FORM_DATA;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/{memberId}")
    @Operation(summary = "멤버 조회 API",
            description = "사용자의 닉네임과 프로필 이미지, 최애구단을 조회하는 API입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content),
    })
    public ApiResponse<MemberResponseDTO.memberProfileDTO> getMemberProfile(@PathVariable Long memberId) {

        Optional<Member> member = memberRepository.findById(memberId);

        MemberResponseDTO.memberProfileDTO response = memberService.getMemberProfile(memberId);

        return ApiResponse.onSuccess(response);
    }

    @PatchMapping("/profile/{memberId}")
    @Operation(summary = "멤버 프로필 변경 API",
            description = "사용자의 닉네임과 최애구단을 수정하는 API입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content),
    })
    public ApiResponse<MemberResponseDTO.memberProfileDTO> updateMemberProfile(
            @RequestBody MemberUpdateDTO memberUpdateDTO, @PathVariable Long memberId) {

        Optional<Member> member = memberRepository.findById(memberId);

        MemberResponseDTO.memberProfileDTO response = memberService.updateProfile(memberUpdateDTO, memberId);

        return ApiResponse.onSuccess(response);
    }

    @PatchMapping(value = "/profile/img/{memberId}",
            consumes = MULTIPART_FORM_DATA,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "멤버 프로필 이미지 변경 API",
            description = "사용자의 프로필 이미지를 수정하는 API입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content),
    })
    public ApiResponse<MemberResponseDTO.ProfileImgDTO> updateMemberProfileImg(
            @ModelAttribute MemberUpdateDTO.profileImg updateImg, @PathVariable Long memberId) {

        Optional<Member> member = memberRepository.findById(memberId);

        MemberResponseDTO.ProfileImgDTO response = memberService.updateProfileImg(updateImg, memberId);

        return ApiResponse.onSuccess(response);
    }
}
