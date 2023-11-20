package com.yeogiya.entity.diary;

import com.yeogiya.entity.member.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class Place {
    @Id
    @Column(name = "id", columnDefinition = "INT(11)")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "kakao_id")
    private int kakaoId;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @OneToMany(mappedBy = "place", orphanRemoval = true, targetEntity = DiaryPlace.class)
    private List<DiaryPlace> diaryPlaces = new ArrayList<>();


}
