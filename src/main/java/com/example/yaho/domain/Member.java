package com.example.yaho.domain;

import com.example.yaho.domain.common.BaseEntity;
import com.example.yaho.domain.enums.FavoriteClub;
import com.example.yaho.web.dto.MemberRequestDTO;
import com.example.yaho.web.dto.MemberUpdateDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "social_id")
    private Long socialId;

    @Column(length = 50)
    private String email;

    @Column(length = 15)
    private String nickname;

    @Column(name = "profile_image", length = 255)
    private String profileImage;

    @Column(name = "password", length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    private FavoriteClub favoriteClub;

    @Column(name = "created_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "member") // Diary 엔티티에서 member 매핑됨
    private List<Diary> diaries; // Diary 리스트

    // 멤버의 프로필 create
    public void create(MemberRequestDTO.CreateMemberDTO request, String profileImage) {
        this.nickname = request.getNickname();
        this.favoriteClub = request.getFavoriteClub();
        this.profileImage = profileImage;

        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    // 멤버의 프로필 수정(닉네임, 최애구단)
    public void update(MemberUpdateDTO memberUpdateDTO) {
        this.nickname = memberUpdateDTO.getNickname();
        this.favoriteClub = memberUpdateDTO.getFavoriteClub();

        LocalDateTime now = LocalDateTime.now();
        this.updatedAt = now;
    }

    // 멤버의 프로필 이미지 수정
    public void updateProfileImg(String profileImg){
        this.profileImage = profileImg;

        LocalDateTime now = LocalDateTime.now();
        this.updatedAt = now;
    }
}
