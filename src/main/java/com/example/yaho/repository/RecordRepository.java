package com.example.yaho.repository;

import com.example.yaho.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query(value = "SELECT r FROM Record r WHERE r.memberId = :memberId ORDER BY r.id DESC")
    List<Record> findLatestRecordsByMemberId(@Param("memberId") Long memberId);
}

