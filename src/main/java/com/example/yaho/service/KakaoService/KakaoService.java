package com.example.yaho.service.KakaoService;

import com.example.yaho.domain.Member;
import com.example.yaho.repository.MemberRepository;
import com.example.yaho.web.dto.KakaoAccountDto;
import com.example.yaho.web.dto.KakaoTokenDto;
import com.example.yaho.web.dto.LoginResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoService {

    private final MemberRepository memberRepository;

    @Value("${kakao.client_id}")
    private String KAKAO_CLIENT_ID;

    @Value("${kakao.client_secret}")
    private String KAKAO_CLIENT_SECRET;

    @Value("${kakao.redirect_uri}")
    private String KAKAO_REDIRECT_URI;

    @Value("${kakao.token_uri}")
    private String KAKAO_TOKEN_URI;

    @Value("${kakao.user_info_uri}")
    private String KAKAO_USER_INFO_URI;

    //카카오 토근 가져오기
    @Transactional

    public KakaoTokenDto getKakaoAccessToken(String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // Http Response Body 객체 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code"); //카카오 공식문서 기준 authorization_code 로 고정
        params.add("client_id", KAKAO_CLIENT_ID); // 카카오 Dev 앱 REST API 키
        params.add("redirect_uri", KAKAO_REDIRECT_URI); // 카카오 Dev redirect uri
        params.add("code", code); // 프론트에서 인가 코드 요청시 받은 인가 코드값
        params.add("client_secret", KAKAO_CLIENT_SECRET); // 카카오 Dev 카카오 로그인 Client Secret

        // 헤더와 바디 합치기 위해 Http Entity 객체 생성
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // 카카오로부터 Access token 받아오기
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                KAKAO_TOKEN_URI, // "https://kauth.kakao.com/oauth/token"
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // JSON Parsing (-> KakaoTokenDto)
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        KakaoTokenDto kakaoTokenDto = null;
        try {
            kakaoTokenDto = objectMapper.readValue(accessTokenResponse.getBody(), KakaoTokenDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoTokenDto;
    }

    public LoginResponseDto kakaoLogin(String kakaoAccessToken) {
        Member member = getKakaoInfo(kakaoAccessToken);

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setLoginSuccess(true);
        loginResponseDto.setMember(member);
        loginResponseDto.setAccessToken(kakaoAccessToken);

        Member existOwner = memberRepository.findById(member.getId()).orElse(null);
        try {
            if (existOwner == null) {
                System.out.println("처음 로그인 하는 회원입니다.");
                memberRepository.save(member);
            }
            loginResponseDto.setLoginSuccess(true);

            return loginResponseDto;


        } catch (Exception e) {
            loginResponseDto.setLoginSuccess(false);
            return loginResponseDto;
        }
    }

    //사용자 정보 가져오기
    public Member getKakaoInfo(String kakaoAccessToken) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoAccessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> accountInfoRequest = new HttpEntity<>(headers);

        // POST 방식으로 API 서버에 요청 후 response 받아옴
        ResponseEntity<String> accountInfoResponse = rt.exchange(
                KAKAO_USER_INFO_URI, // "https://kapi.kakao.com/v2/user/me"
                HttpMethod.POST,
                accountInfoRequest,
                String.class
        );

        // JSON Parsing (-> kakaoAccountDto)
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        KakaoAccountDto kakaoAccountDto = null;
        try {
            kakaoAccountDto = objectMapper.readValue(accountInfoResponse.getBody(), KakaoAccountDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 회원가입 처리하기
        Long kakaoId = kakaoAccountDto.getId();
        Member existOwner = memberRepository.findById(kakaoId).orElse(null);
        // 처음 로그인이 아닌 경우
        if (existOwner != null) {
            return existOwner;
        }
        // 처음 로그인 하는 경우
        else {
            return Member.builder()
                    .id(kakaoAccountDto.getId())
                    .nickname(kakaoAccountDto.getKakaoAccount().getProfile().getNickName())
                    .profileImage(kakaoAccountDto.getKakaoAccount().getProfile().getProfileImageUrl())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
        }
    }

    // KakaoAccountDto 반환 메서드 추가 (카카오 계정 정보 가져오기)
    public KakaoAccountDto getKakaoAccountDto(String kakaoAccessToken) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoAccessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> accountInfoRequest = new HttpEntity<>(headers);

        ResponseEntity<String> accountInfoResponse = rt.exchange(
                KAKAO_USER_INFO_URI,
                HttpMethod.POST,
                accountInfoRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        KakaoAccountDto kakaoAccountDto = null;
        try {
            kakaoAccountDto = objectMapper.readValue(accountInfoResponse.getBody(), KakaoAccountDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoAccountDto;
    }

    // 카카오 로그아웃
    public void kakaoLogout(String kakaoAccessToken) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoAccessToken);

        HttpEntity<MultiValueMap<String, String>> logoutRequest = new HttpEntity<>(headers);

        // POST 방식으로 카카오 로그아웃 API 호출
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v1/user/logout",
                HttpMethod.POST,
                logoutRequest,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("카카오 로그아웃 성공");
        } else {
            log.error("카카오 로그아웃 실패: " + response.getBody());
        }
    }

    // 토큰 유효성 확인
    private static final String KAKAO_TOKEN_INFO_URL = "https://kapi.kakao.com/v1/user/access_token_info";

    public void checkAccessToken(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        // 요청 보내기
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(KAKAO_TOKEN_INFO_URL, HttpMethod.GET, entity, String.class);

            // 응답 결과 확인
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("토큰이 유효합니다.");
                System.out.println("응답 본문: " + response.getBody());
            }
        } catch (HttpClientErrorException e) {
            // 예외 처리
            if (e.getStatusCode().is4xxClientError()) {
                System.out.println("토큰이 유효하지 않습니다. 상태 코드: " + e.getStatusCode());
                System.out.println("에러 메시지: " + e.getMessage());
            } else {
                System.out.println("예상치 못한 에러가 발생했습니다: " + e.getMessage());
            }
        }
    }

    //회원 탈퇴
    // 회원탈퇴 (카카오 연결끊기)
    public void kakaoUnlink(String kakaoAccessToken) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + kakaoAccessToken);

        HttpEntity<MultiValueMap<String, String>> unlinkRequest = new HttpEntity<>(headers);

        try {
            // 먼저 액세스 토큰이 유효한지 확인
            checkAccessToken(kakaoAccessToken);

            ResponseEntity<String> response = rt.exchange(
                    "https://kapi.kakao.com/v1/user/unlink",
                    HttpMethod.POST,
                    unlinkRequest,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("카카오 회원탈퇴 성공");
            } else {
                log.error("카카오 회원탈퇴 실패: 상태코드: " + response.getStatusCode());
                log.error("응답 본문: " + response.getBody());
                throw new RuntimeException("카카오 회원탈퇴에 실패했습니다.");
            }
        } catch (HttpClientErrorException e) {
            log.error("카카오 회원탈퇴 과정에서 예외 발생: 상태코드: " + e.getStatusCode());
            log.error("에러 메시지: " + e.getResponseBodyAsString());
            throw new RuntimeException("카카오 회원탈퇴에 실패했습니다.", e);
        } catch (Exception e) {
            log.error("예상치 못한 에러가 발생했습니다: " + e.getMessage());
            throw new RuntimeException("카카오 회원탈퇴에 실패했습니다.", e);
        }
    }

    public void kakaoUnlinkAndDeleteMember(String kakaoAccessToken, Long memberId) {
        // 카카오 연결 끊기
        kakaoUnlink(kakaoAccessToken);

        // DB에서 회원 삭제
        memberRepository.deleteById(memberId);
    }
    /*public void kakaoUnlink(String kakaoAccessToken) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoAccessToken);

        HttpEntity<MultiValueMap<String, String>> unlinkRequest = new HttpEntity<>(headers);

        // 카카오 회원탈퇴 API 호출
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v1/user/unlink",
                HttpMethod.POST,
                unlinkRequest,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("카카오 회원탈퇴 성공");

            // 카카오 사용자 정보 가져오기
            KakaoAccountDto kakaoAccountDto = getKakaoAccountDto(kakaoAccessToken);
            Long kakaoId = kakaoAccountDto.getId();

            // 데이터베이스에서 회원 삭제
            try {
                memberRepository.deleteById(kakaoId);
                log.info("회원 ID {}가 데이터베이스에서 삭제되었습니다.", kakaoId);
            } catch (Exception e) {
                log.error("회원 ID {} 삭제 중 오류 발생: {}", kakaoId, e.getMessage());
            }
        } else {
            log.error("카카오 회원탈퇴 실패: " + response.getBody());
        }
    }*/
    /*public ResponseEntity<String> kakaoUnlink(String kakaoAccessToken) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + kakaoAccessToken);

        HttpEntity<MultiValueMap<String, String>> unlinkRequest = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = rt.exchange(
                    "https://kapi.kakao.com/v1/user/unlink",
                    HttpMethod.POST,
                    unlinkRequest,
                    String.class
            );

            log.info("Response: " + response.getStatusCode() + " - " + response.getBody());

            if (response.getStatusCode() == HttpStatus.OK) {
                // 정상적으로 처리된 경우
                KakaoAccountDto kakaoAccountDto = getKakaoAccountDto(kakaoAccessToken);
                Long kakaoId = kakaoAccountDto.getId();
                Member member = memberRepository.findById(kakaoId).orElse(null);

                if (member != null) {
                    memberRepository.delete(member);
                    log.info("카카오 회원탈퇴 성공");
                    return ResponseEntity.ok("회원탈퇴 성공");
                } else {
                    log.error("회원 정보를 찾을 수 없습니다. ID: " + kakaoId);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 정보를 찾을 수 없습니다.");
                }
            } else {
                log.error("카카오 회원탈퇴 실패: " + response.getBody());
                return ResponseEntity.status(response.getStatusCode()).body("카카오 회원탈퇴 실패");
            }
        } catch (HttpClientErrorException e) {
            log.error("HTTP Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
            return ResponseEntity.status(e.getStatusCode()).body("Unauthorized");
        } catch (Exception e) {
            log.error("서버 에러 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러 발생");
        }
    }*/

    /*public void kakaoUnlink(String kakaoAccessToken) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoAccessToken);

        HttpEntity<MultiValueMap<String, String>> unlinkRequest = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = rt.exchange(
                    "https://kapi.kakao.com/v1/user/unlink",
                    HttpMethod.POST,
                    unlinkRequest,
                    String.class
            );

            log.info("Response: " + response.getStatusCode() + " - " + response.getBody());

            if (response.getStatusCode() == HttpStatus.OK) {
                KakaoAccountDto kakaoAccountDto = getKakaoAccountDto(kakaoAccessToken);
                Long kakaoId = kakaoAccountDto.getId();

                Member member = memberRepository.findById(kakaoId).orElse(null);

                if (member != null) {
                    memberRepository.delete(member);
                    log.info("카카오 회원탈퇴 성공");
                } else {
                    log.error("회원 정보를 찾을 수 없습니다. ID: " + kakaoId);
                }
            } else {
                log.error("카카오 회원탈퇴 실패: " + response.getBody());
            }
        } catch (HttpClientErrorException e) {
            log.error("HTTP Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            log.error("서버 에러 발생", e);
        }

        POST 방식으로 카카오 로그아웃 API 호출
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v1/user/unlink",
                HttpMethod.POST,
                unlinkRequest,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            Member member = getKakaoInfo(kakaoAccessToken);
            memberRepository.delete(member);
            log.info("카카오 회원탈퇴 성공");
        } else {
            log.error("카카오 회원탈퇴 실패: " + response.getBody());
        }
    }*/

}