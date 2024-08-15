package com.example.yaho.repository;

import com.example.yaho.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    // LocalDate 형식을 사용하여 데이터를 검색
    List<Game> findByDate(LocalDate date);

    Optional<Game> findByDateAndLocation(LocalDate date, String location);
}
