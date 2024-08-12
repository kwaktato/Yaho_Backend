package com.example.yaho.service.DiaryService;

import com.example.yaho.apiPayload.code.status.ErrorStatus;
import com.example.yaho.apiPayload.exception.handler.GameIdHandler;
import com.example.yaho.aws.s3.AmazonS3Manager;
import com.example.yaho.converter.DiaryConverter;
import com.example.yaho.domain.Diary;
import com.example.yaho.domain.Game;
import com.example.yaho.domain.Uuid;
import com.example.yaho.repository.DiaryRepository;
import com.example.yaho.repository.GameRepository;
import com.example.yaho.repository.UuidRepository;
import com.example.yaho.web.dto.DiaryRequestDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiaryCommandServiceImpl implements DiaryCommandService {

    private final DiaryRepository diaryRepository;
    private final GameRepository gameRepository;
    private final AmazonS3Manager s3Manager;
    private final UuidRepository uuidRepository;

    @Override
    @Transactional
    public Diary writeDiary(Long gameId, DiaryRequestDTO.WriteDto request) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameIdHandler(ErrorStatus.GAME_ID_NOT_FOUND));

        Diary newDiary = DiaryConverter.toDiary(request, game);

        if (request.getMvpPicture() != null) {
            String uuid = UUID.randomUUID().toString();
            Uuid savedUuid = uuidRepository.save(Uuid.builder()
                    .uuid(uuid).build());


            String pictureUrl = s3Manager.uploadFile(s3Manager.generateMvpKeyName(savedUuid), request.getMvpPicture());
            newDiary.setMvpImageUrl(pictureUrl);
        }

        return diaryRepository.save(newDiary);
    }
}
