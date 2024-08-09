package com.example.yaho.service.MemberService;

import com.example.yaho.domain.Member;
import com.example.yaho.repository.MemberRepository;

import com.example.yaho.web.dto.MemberResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponseDTO.memberProfileDTO getMemberProfile(String memberName) {

        Member member = memberRepository.findByNickname(memberName).orElseThrow(()->{throw new IllegalArgumentException("멤버없음");});

        String nickname = member.getNickname();
        String profileImgUrl = member.getProfileImage();

        return MemberResponseDTO.memberProfileDTO.builder()
                .nickName(nickname)
                .profileImgUrl(profileImgUrl)
                .build();
    }
}
