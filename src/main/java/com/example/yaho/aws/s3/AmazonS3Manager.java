package com.example.yaho.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.yaho.config.AmazonConfig;
import com.example.yaho.domain.Uuid;
import com.example.yaho.repository.UuidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Manager{

    private final AmazonS3 amazonS3;

    private final AmazonConfig amazonConfig;

    private final UuidRepository uuidRepository;

    public String uploadFile(String keyName, MultipartFile file) {
        String originalFilename = file.getOriginalFilename(); //원본 파일 명
        String extention = originalFilename.substring(originalFilename.lastIndexOf("."));

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        try {
            amazonS3.putObject(new PutObjectRequest(amazonConfig.getBucket(), keyName, file.getInputStream(), metadata));
        } catch (IOException e){
            log.error("error at AmazonS3Manager uploadFile : {}", (Object) e.getStackTrace());
        }

        // extention 제외 : 미리 업로드 된 emotion과 구단 로고 외에 업로드하는 사진엔 .png같은 확장자가 붙어있지 않음
        // extention을 사용하면 사진이 안열리는 오류 발생
        return amazonS3.getUrl(amazonConfig.getBucket(), keyName).toString();
    }

    public String generateMvpKeyName(Uuid uuid) {
        return amazonConfig.getMvpPath() + '/' + uuid.getUuid();
    }

    public String generateProfileImgName(Uuid uuid) {
        return amazonConfig.getProfileImg() + '/' + uuid.getUuid();
    }
}
