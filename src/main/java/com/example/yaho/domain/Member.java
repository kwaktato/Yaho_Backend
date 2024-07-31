package com.example.yaho.domain;

import com.example.yaho.domain.common.BaseEntity;
import com.example.yaho.domain.enums.FairyGrade;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


import java.time.LocalDateTime;

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

    @Column(name = "Field", length = 255)
    private String field;

    @Enumerated(EnumType.STRING)
    private FairyGrade fairyGrade;

    @Column(name = "created_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime updatedAt;

}
