package com.example.yaho.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity // JPA 엔티티임을 명시
@Getter // lombok에서 제공
@Setter
@Builder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "diary")
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // game_id 와 중복
//    @Column(name = "id2", nullable = false)
//    private Long id2;

    @Column(length = 255)
    private LocalDate date;

    @Column(length = 255)
    private String emoticon;

    @Column(length = 255)
    private String mvp;

    @Column(length = 255)
    private String content;

    @ManyToOne
    @JoinColumn(name = "game_id") // 외래 키로 사용할 컬럼 이름
    private Game game; // Game 참조

    private String mvpImageUrl;

    @Column(name = "created_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime updatedAt;

}
