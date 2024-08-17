package com.example.yaho.repository;

import com.example.yaho.domain.Diary;
import com.example.yaho.domain.Game;
import com.example.yaho.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.time.LocalDate;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Diary findByDate(LocalDate date);

    List<Diary> findTop9ByMember_IdOrderByDateDesc(Long memberId);

//    Optional<Diary> findByDate(LocalDate date);
//    Optional<Diary> findByGame(Game game);

    Optional<Diary> findByDateAndMember(LocalDate date, Member member);

    Optional<Diary> findByGameAndMember(Game game, Member member);
}
