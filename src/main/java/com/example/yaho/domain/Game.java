package com.example.yaho.domain;

import com.example.yaho.domain.enums.Location;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
@Entity
@Getter
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 날짜 형식 포맷팅을 위한 수정
    @Column(name = "date", columnDefinition = "DATE")
    private LocalDate date;

    @Column(name = "team_1", length = 30)
    private String team1;

    @Column(name = "team_2", length = 30)
    private String team2;

    @Enumerated(EnumType.STRING)
    @Column(name = "location")
    private Location location;

    @Column(length = 30)
    private String time;

    @OneToMany(mappedBy = "game") // Diary 엔티티에서 game 매핑됨
    private List<Diary> diaries; // Diary 리스트
}
