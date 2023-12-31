package com.yeogiya.web.diary.dto.response;

import com.yeogiya.entity.diary.Diary;
import com.yeogiya.entity.diary.DiaryHashtag;
import com.yeogiya.entity.diary.DiaryImage;
import com.yeogiya.entity.diary.Hashtag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiariesResponseDTO {

    private Long diaryId;
    private Long memberId;
    private String placeName;

    private String openYn;
    private Double star;

    private LocalDate date;
    private String diaryImage;


    public DiariesResponseDTO(Diary diary){
        this.diaryId = diary.getId();
        this.openYn=diary.getOpenYn().toString();
        this.memberId = diary.getMember().getMemberId();
        this.star = diary.getStar();
        this.date = diary.getDate();

        this.diaryImage = diary.getThumb();

        this.placeName = diary.getDiaryPlace().getPlace().getName();


    }
}

