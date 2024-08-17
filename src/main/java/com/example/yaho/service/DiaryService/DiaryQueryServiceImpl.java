package com.example.yaho.service.DiaryService;

import com.example.yaho.domain.Member;
import com.example.yaho.repository.MemberRepository;
import com.example.yaho.web.dto.DiaryRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.yaho.domain.Diary;
import com.example.yaho.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryQueryServiceImpl implements DiaryQueryService{

    private final MemberRepository memberRepository;
   private final DiaryRepository diaryRepository;

    @Override
    public String getEmotionImageUrl(DiaryRequestDTO.EmotionDto request) {

        String emotionImageUrl = "";

        switch (request.getEmotionImage().intValue()) {
            case 1:
                emotionImageUrl = "https://yahobucket.s3.ap-northeast-2.amazonaws.com/emotions/1.png";
                break;
            case 2:
                emotionImageUrl = "https://yahobucket.s3.ap-northeast-2.amazonaws.com/emotions/2.png";
                break;
            case 3:
                emotionImageUrl = "https://yahobucket.s3.ap-northeast-2.amazonaws.com/emotions/3.png";
                break;
            case 4:
                emotionImageUrl = "https://yahobucket.s3.ap-northeast-2.amazonaws.com/emotions/4.png";
                break;
            case 5:
                emotionImageUrl = "https://yahobucket.s3.ap-northeast-2.amazonaws.com/emotions/5.png";
                break;
            case 6:
                emotionImageUrl = "https://yahobucket.s3.ap-northeast-2.amazonaws.com/emotions/6.png";
                break;
            case 7:
                emotionImageUrl = "https://yahobucket.s3.ap-northeast-2.amazonaws.com/emotions/7.png";
                break;
            case 8:
                emotionImageUrl = "https://yahobucket.s3.ap-northeast-2.amazonaws.com/emotions/8.png";
                break;
            case 9:
                emotionImageUrl = "https://yahobucket.s3.ap-northeast-2.amazonaws.com/emotions/9.png";
                break;
        }
        return emotionImageUrl;
    }

    @Override
    public String getFavoriteClubImageUrl(Long memberId) {

        Member member = memberRepository.findById(memberId).get();

        String favoriteClubImageUrl = "";

        switch (member.getFavoriteClub()) {
            case SSG_LANDERS:
                favoriteClubImageUrl = "https://yahobucket.s3.ap-northeast-2.amazonaws.com/clubs/SSG.png";
                break;
            case LG_TWINS:
                favoriteClubImageUrl = "https://yahobucket.s3.ap-northeast-2.amazonaws.com/clubs/LG.png";
                break;
            case KT_WIZ:
                favoriteClubImageUrl = "https://yahobucket.s3.ap-northeast-2.amazonaws.com/clubs/KT.png";
                break;
            case NC_DINOS:
                favoriteClubImageUrl = "https://yahobucket.s3.ap-northeast-2.amazonaws.com/clubs/NC.png";
                break;
            case DOOSAN_BEARS:
                favoriteClubImageUrl = "https://yahobucket.s3.ap-northeast-2.amazonaws.com/clubs/DOOSAN.png";
                break;
            case KIA_TIGERS:
                favoriteClubImageUrl = "https://yahobucket.s3.ap-northeast-2.amazonaws.com/clubs/KIA.png";
                break;
            case LOTTE_GIANTS:
                favoriteClubImageUrl = "https://yahobucket.s3.ap-northeast-2.amazonaws.com/clubs/LOTTE.png";
                break;
            case SAMSUNG_LIONS:
                favoriteClubImageUrl = "https://yahobucket.s3.ap-northeast-2.amazonaws.com/clubs/SAMSUNG.png";
                break;
            case HANWHA_EAGLES:
                favoriteClubImageUrl = "https://yahobucket.s3.ap-northeast-2.amazonaws.com/clubs/HANWHA.png";
                break;
            case KIWOOM_HEROES:
                favoriteClubImageUrl = "https://yahobucket.s3.ap-northeast-2.amazonaws.com/clubs/KIWOOM.png";
                break;
        }
        return favoriteClubImageUrl;

    }
  
  @Override
    @Transactional
    public Diary getDiary(DiaryRequestDTO.GetDto request) {

        return diaryRepository.findByDate(request.getDate());
    }


}
