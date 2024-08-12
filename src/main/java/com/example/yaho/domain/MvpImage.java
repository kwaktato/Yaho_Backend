//package com.example.yaho.domain;
//
//import com.example.yaho.domain.common.BaseEntity;
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Getter
//@Builder
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
//public class MvpImage extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "diary_id")
//    private Diary diary;
//
//    private String url;
//
//    public void setDiary(Diary diary){
////        if(this.diary != null)
////            diary.getMvpImageList().remove(this);
//        this.diary = diary;
//        diary.getMvpImageList().add(this);
//    }
//}
