package com.example.yaho.repository;

import com.example.yaho.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query("SELECT r FROM Record r WHERE r.memberId = :memberId ORDER BY r.id DESC")
    Optional<Record> findLatestRecordByMemberId(@Param("memberId") Long memberId);
}
