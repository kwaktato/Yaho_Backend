package com.example.yaho.domain;

import com.example.yaho.domain.common.BaseEntity;
import com.example.yaho.domain.enums.FavoriteClub;
import com.example.yaho.web.dto.MemberUpdateDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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


    // 멤버의 프로필 수정(닉네임, 최애구단)
    public void update(MemberUpdateDTO memberUpdateDTO) {
        this.nickname = memberUpdateDTO.getNickname();
        this.favoriteClub = memberUpdateDTO.getFavoriteClub();
    }

    // 멤버의 프로필 이미지 수정
    public void updateProfileImg(String profileImg){
        this.profileImage = profileImg;
    }
    @OneToMany(mappedBy = "member") // Diary 엔티티에서 member 매핑됨
    private List<Diary> diaries; // Diary 리스트

}
