package com.yeogiya.entity.diary;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryImage {
    @Id
    @Column(name = "id", columnDefinition = "INT(11)")
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "saved_name")
    private String savedName;

    @Column(name = "path")
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;


}
