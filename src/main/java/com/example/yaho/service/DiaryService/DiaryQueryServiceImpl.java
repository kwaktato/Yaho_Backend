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

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiaryQueryServiceImpl implements DiaryQueryService{

    private final DiaryRepository diaryRepository;
    private final GameRepository gameRepository;
    private final AmazonS3Manager s3Manager;
    private final UuidRepository uuidRepository;

    @Override
    @Transactional
    public Diary getDiary(DiaryRequestDTO.GetDto request) {

        return diaryRepository.findByDate(request.getDate());
    }

    @Override
    @Transactional
    public Diary modifyDiary(DiaryRequestDTO.ModifyDto request) {
        // Retrieve the diary based on the date and location provided in the request
        Game game = gameRepository.findByDateAndLocation(request.getDate(), request.getLocation())
                .orElseThrow(() -> new GameIdHandler(ErrorStatus.GAME_ID_NOT_FOUND));

        Diary diary = diaryRepository.findByGame(game).orElseThrow(() -> new GameIdHandler(ErrorStatus.GAME_ID_NOT_FOUND));


        // Modify the diary using the converter
        diary = DiaryConverter.modifyDiary(request, diary);
        diary.setGame(game);

        // If there's an MVP picture, upload it to S3 and update the diary
        if (request.getMvpPicture() != null) {
            String uuid = UUID.randomUUID().toString();
            Uuid savedUuid = uuidRepository.save(Uuid.builder()
                    .uuid(uuid).build());

            // Generate the S3 key and upload the file
            String pictureUrl = s3Manager.uploadFile(s3Manager.generateMvpKeyName(savedUuid), request.getMvpPicture());
            diary.setMvpImageUrl(pictureUrl);
        }

        // Save and return the updated diary
        return diaryRepository.save(diary);
    }

}
