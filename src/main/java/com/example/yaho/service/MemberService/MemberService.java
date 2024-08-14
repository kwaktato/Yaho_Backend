package com.example.yaho.service.MemberService;

import com.example.yaho.apiPayload.code.status.ErrorStatus;
import com.example.yaho.apiPayload.exception.handler.MemberHandler;
import com.example.yaho.aws.s3.AmazonS3Manager;
import com.example.yaho.converter.MemberConverter;
import com.example.yaho.domain.Member;
import com.example.yaho.domain.Uuid;
import com.example.yaho.domain.enums.FavoriteTeam;
import com.example.yaho.repository.MemberRepository;

import com.example.yaho.repository.UuidRepository;
import com.example.yaho.web.dto.MemberResponseDTO;
import com.example.yaho.web.dto.MemberUpdateDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 프로필 조회
     */
    public MemberResponseDTO.memberProfileDTO getMemberProfile(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Long id = member.getId();
        String nickname = member.getNickname();
        String profileImgUrl = member.getProfileImage();
        FavoriteTeam favoriteTeam = member.getFavoriteTeam();

        return MemberResponseDTO.memberProfileDTO.builder()
                .nickname(nickname)
                .profileImgUrl(profileImgUrl)
                .favoriteTeam(favoriteTeam)
                .build();
    }

    /***
     * 프로필 수정(닉네임, 최애구단)
     * @param memberUpdateDTO
     * @return
     */
    public MemberResponseDTO.memberProfileDTO updateProfile(MemberUpdateDTO memberUpdateDTO, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        member.update(memberUpdateDTO);
        memberRepository.save(member);

        return MemberConverter.toProfileDTO(member);
    }

    private final AmazonS3Manager s3Manager;
    private final UuidRepository uuidRepository;

    /***
     * 프로필 이미지 수정
     * @param updateImg
     * @param memberId
     * @return
     */
    public MemberResponseDTO.ProfileImgDTO updateProfileImg(MemberUpdateDTO.profileImg updateImg, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Uuid imgUuid = uuidRepository.save(Uuid.builder().uuid(UUID.randomUUID().toString()).build());
        String imgURL = s3Manager.uploadFile(s3Manager.generateProfileImgName(imgUuid), updateImg.getProfileImg());

        member.updateProfileImg(imgURL);

        return MemberResponseDTO.ProfileImgDTO.builder()
                .memberId(memberId)
                .profileImgURL(imgURL)
                .build();
    }
}
