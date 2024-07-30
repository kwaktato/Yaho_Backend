package com.example.yaho.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "diary")
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id2", nullable = false)
    private Long id2;

    @Column(length = 255)
    private String date;

    @Column(length = 255)
    private String emoticon;

    @Column(length = 255)
    private String mvp;

    @Column(length = 255)
    private String content;

    @ManyToOne
    @JoinColumn(name = "game_id") // 외래 키로 사용할 컬럼 이름
    private Game game; // Game 참조

    @Column(name = "created_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime updatedAt;
    
}
