package com.example.yaho.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "record")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(length = 255)
    private String totalGames;

    @Column(length = 255)
    private String winningGames;

    @Column(length = 255)
    private String winningRate;


}
