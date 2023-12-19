package com.yeogiya.entity.diary;

import com.yeogiya.entity.BaseTimeEntity;
import com.yeogiya.entity.member.Member;

import lombok.*;


import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary extends BaseTimeEntity {

    @Id
    @Column(name = "id", columnDefinition = "INT(11)")
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(name = "content", columnDefinition = "VARCHAR(2000)")
    private String content;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "open_yn", columnDefinition = "CHAR(1)")
    private OpenYn openYn;

    @Column(name = "star", columnDefinition = "DECIMAL(2,1)")
    private Double star;

    @Column(name = "date")
    private LocalDate date;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaryHashtag> diaryHashtags;

    @OneToMany(mappedBy = "diary", orphanRemoval = true)
    private List<DiaryImage> diaryImages;

    @OneToOne(mappedBy = "diary", orphanRemoval = true)
    private DiaryPlace diaryPlace;

    public void update(String content, OpenYn openYn, Double star, LocalDate date) {
        this.content = content;
        this.openYn = openYn;
        this.star = star;
        this.date = date;
    }






}
