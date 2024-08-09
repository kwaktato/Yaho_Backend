package com.example.yaho.service.RecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.yaho.domain.Record;
import com.example.yaho.repository.RecordRepository;

@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    public Record createRecord(Long memberId, String totalGames, String winningGames) {
        // winningRate 계산
        double totalGamesNum = Double.parseDouble(totalGames);
        double winningGamesNum = Double.parseDouble(winningGames);

        // winningRate 계산 (0으로 나누는 경우를 방지)
        String winningRate = totalGamesNum == 0 ? "0" : String.valueOf(winningGamesNum / totalGamesNum);

        // Record 객체 생성 및 저장
        Record record = new Record();
        record.setMemberId(memberId);
        record.setTotalGames(totalGames);
        record.setWinningGames(winningGames);
        record.setWinningRate(winningRate);

        return recordRepository.save(record);
    }
}

