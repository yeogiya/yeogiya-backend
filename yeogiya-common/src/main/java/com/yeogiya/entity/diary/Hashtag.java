package com.yeogiya.entity.diary;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor
@Entity
public class Hashtag {
    @Id
    @Column(name = "id", columnDefinition = "INT(11)")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "hashtag", orphanRemoval = true, targetEntity = DiaryHashtag.class)
    private List<DiaryHashtag> diaryHashtags = new ArrayList<>();

    @Builder
    public Hashtag(String name) {
        this.name = name;
    }
}
