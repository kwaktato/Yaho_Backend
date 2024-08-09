package com.example.yaho.service.ScheduleService;

import com.example.yaho.domain.Game;

import java.util.List;

public interface ScheduleCommandService {
    List<Game> getTodayGames();
}