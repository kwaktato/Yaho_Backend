package com.example.yaho.domain;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String date;

    @Column(name = "team_1", length = 30)
    private String team1;

    @Column(name = "team_2", length = 30)
    private String team2;

    @Column(length = 30)
    private String location;

    @OneToMany(mappedBy = "game") // Diary 엔티티에서 game 매핑됨
    private List<Diary> diaries; // Diary 리스트
}
