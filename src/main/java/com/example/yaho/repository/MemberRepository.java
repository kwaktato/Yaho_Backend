package com.example.yaho.repository;

import com.example.yaho.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findBySocialId(Long socialId);

    boolean existsByNickname(String nickname);

}
