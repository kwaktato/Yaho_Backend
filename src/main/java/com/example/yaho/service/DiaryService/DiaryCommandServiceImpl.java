package com.example.yaho.service.DiaryService;

import com.example.yaho.apiPayload.code.status.ErrorStatus;
import com.example.yaho.apiPayload.exception.handler.GameIdHandler;
import com.example.yaho.converter.DiaryConverter;
import com.example.yaho.domain.Diary;
import com.example.yaho.domain.Game;
import com.example.yaho.repository.DiaryRepository;
import com.example.yaho.repository.GameRepository;
import com.example.yaho.web.dto.DiaryRequestDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryCommandServiceImpl implements DiaryCommandService {

    private final DiaryRepository diaryRepository;
    private final GameRepository gameRepository;

    @Override
    @Transactional
    public Diary writeDiary(Long gameId, DiaryRequestDTO.WriteDto request) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameIdHandler(ErrorStatus.GAME_ID_NOT_FOUND));

        Diary newDiary = DiaryConverter.toDiary(request, game);

        return diaryRepository.save(newDiary);
    }
}
