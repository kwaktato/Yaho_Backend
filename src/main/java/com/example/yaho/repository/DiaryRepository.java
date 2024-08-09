package com.example.yaho.repository;

import com.example.yaho.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Diary findByDate(LocalDate date);
}
