package com.example.yaho.repository;

import com.example.yaho.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findBySocialId(Long socialId);

    boolean existsByNickname(String nickname);

    // SocialId로 존재 여부 확인
    boolean existsBySocialId(Long socialId);

    // SocialId로 회원 정보 삭제
    void deleteBySocialId(Long socialId);

}
