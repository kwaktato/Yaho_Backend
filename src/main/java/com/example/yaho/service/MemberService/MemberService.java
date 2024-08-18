package com.example.yaho.service.MemberService;

import com.example.yaho.apiPayload.code.status.ErrorStatus;
import com.example.yaho.apiPayload.exception.handler.MemberHandler;
import com.example.yaho.aws.s3.AmazonS3Manager;
import com.example.yaho.converter.MemberConverter;
import com.example.yaho.domain.Diary;
import com.example.yaho.domain.Member;
import com.example.yaho.domain.Uuid;
import com.example.yaho.domain.enums.FavoriteClub;
import com.example.yaho.repository.DiaryRepository;
import com.example.yaho.repository.MemberRepository;

import com.example.yaho.repository.UuidRepository;
import com.example.yaho.web.dto.MemberRequestDTO;
import com.example.yaho.web.dto.MemberResponseDTO;
import com.example.yaho.web.dto.MemberUpdateDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;

    /**
     * 프로필 조회
     */
    public MemberResponseDTO.memberDTO getMemberProfile(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        return MemberConverter.toMemberDTO(member);
    }

    /**
     * 멤버 정보 받아와서 저장하기
     */
    public MemberResponseDTO.memberDTO createMemberInfo(MemberRequestDTO.CreateMemberDTO request) {
            Uuid imgUuid = uuidRepository.save(Uuid.builder().uuid(UUID.randomUUID().toString()).build());
            String imgURL = s3Manager.uploadFile(s3Manager.generateProfileImgName(imgUuid), request.getProfileImg());

            Member member = MemberConverter.toMember(request, imgURL);
            memberRepository.save(member);

            return MemberConverter.toMemberDTO(member);
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
        memberRepository.save(member);

        return MemberResponseDTO.ProfileImgDTO.builder()
                .profileImgURL(imgURL)
                .updatedAt(member.getUpdatedAt())
                .build();
    }

    /**
     * 닉네임 중복 확인
     */
    public boolean checkNicknameExists(String nickname) {
        boolean isExist = memberRepository.existsByNickname(nickname);
        return isExist;
    }

    /**
     * 멤버의 일기 목록 불러오기
     * @param memberId
     * @return
     */
    public MemberResponseDTO.mypageDiaryListDTO getMemberDiaryList(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        List<Diary> diaryList = diaryRepository.findAllByMember_IdOrderByDateDesc(memberId);

        List<MemberResponseDTO.mypageDiaryDTO> mypageDiaryDTOList = diaryList.stream()
                .map(diary -> MemberConverter.toDiaryDTO(diary))
                .toList();

        return MemberResponseDTO.mypageDiaryListDTO.builder()
                .diaryList(mypageDiaryDTOList)
                .build();
    }
}
