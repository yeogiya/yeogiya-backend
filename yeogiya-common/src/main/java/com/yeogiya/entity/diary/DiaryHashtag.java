package com.yeogiya.entity.diary;

import com.yeogiya.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class DiaryHashtag extends BaseTimeEntity {
    @Id
    @Column(name = "id", columnDefinition = "INT(11)")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hashtag_id", nullable = false)
    private Hashtag hashtag;

    @Builder
    public DiaryHashtag(Diary diary, Hashtag hashtag) {
        this.diary = diary;
        this.hashtag = hashtag;
//        hashtag.getDiaryHashtags().add(this);
//        diary.getDiaryHashtags().add(this);

    }
}
