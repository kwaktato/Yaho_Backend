package com.example.yaho.service.DiaryService;

import com.example.yaho.apiPayload.code.status.ErrorStatus;
import com.example.yaho.apiPayload.exception.handler.GameIdHandler;
import com.example.yaho.aws.s3.AmazonS3Manager;
import com.example.yaho.converter.DiaryConverter;
import com.example.yaho.domain.Diary;
import com.example.yaho.domain.Game;
import com.example.yaho.domain.Member;
import com.example.yaho.domain.Uuid;
import com.example.yaho.domain.enums.Location;
import com.example.yaho.repository.DiaryRepository;
import com.example.yaho.repository.GameRepository;
import com.example.yaho.repository.MemberRepository;
import com.example.yaho.repository.UuidRepository;
import com.example.yaho.web.dto.DiaryRequestDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiaryQueryServiceImpl implements DiaryQueryService{

    private final DiaryRepository diaryRepository;
    private final GameRepository gameRepository;
    private final MemberRepository memberRepository;
    private final AmazonS3Manager s3Manager;
    private final UuidRepository uuidRepository;

    @Override
    @Transactional
    public Diary getDiary(Long memberId, LocalDate date) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));


        Diary diary = diaryRepository.findByDateAndMember(date, member)
                .orElseThrow(() -> new RuntimeException("Diary not found"));

        return diary;
    }

    @Override
    @Transactional
    public Diary modifyDiary(Long memberId, DiaryRequestDTO.ModifyDto request) {
        // Retrieve the diary based on the date and location provided in the request
        Game game = gameRepository.findByDateAndLocation(request.getDate(), getLocationFromRequest(request.getLocation()))
                .orElseThrow(() -> new RuntimeException("Game not found"));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));


        Diary diary = diaryRepository.findByGameAndMember(game, member).orElseThrow(() -> new GameIdHandler(ErrorStatus.GAME_ID_NOT_FOUND));


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

    private static Location getLocationFromRequest(int locationCode) {
        switch (locationCode) {
            case 1:
                return Location.GOCHEOK_SKY_DOME;
            case 2:
                return Location.KIA_CHAMPIONS_FIELD;
            case 3:
                return Location.DAEGU_SAMSUNG_LIONS_PARK;
            case 4:
                return Location.SAJIK_BASEBALL_STADIUM;
            case 5:
                return Location.SUWON_KT_WIZ_PARK;
            case 6:
                return Location.INCHEON_SSG_LANDERS_FIELD;
            case 7:
                return Location.JAMSIL_BASEBALL_STADIUM;
            case 8:
                return Location.CHANGWON_NC_PARK;
            default:
                throw new IllegalArgumentException("Invalid location code: " + locationCode);
        }
    }

}
