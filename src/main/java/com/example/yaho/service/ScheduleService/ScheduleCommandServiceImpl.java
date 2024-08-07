package com.example.yaho.service.ScheduleService;

import com.example.yaho.domain.Game;
import com.example.yaho.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleCommandServiceImpl implements ScheduleCommandService {

    private final GameRepository gameRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Game> getTodayGames() {
        // LocalDate 형식을 사용하여 오늘 날짜를 검색
        LocalDate today = LocalDate.now();
        List<Game> games = gameRepository.findByDate(today);
        return games;
    }
}