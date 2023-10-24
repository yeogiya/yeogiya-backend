package com.yeogiya.web.diary.dto.response;

import com.yeogiya.entity.diary.Diary;
import com.yeogiya.entity.diary.DiaryHashtag;
import com.yeogiya.entity.diary.DiaryImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiariesResponseDTO {

    private Long diaryId;
    private Long memberId;
    private String placeName;
    private String content;
    private String openYn;
    private List<String> diaryImages;
    private List<String> hashtags;

    public DiariesResponseDTO(Diary diary){
        this.diaryId = diary.getId();
        this.content=diary.getContent();
        this.openYn=diary.getOpenYn().toString();
        this.memberId = diary.getMember().getMemberId();

        List<String> diaryHashtagStrings = new ArrayList<>();
        List<DiaryHashtag> diaryHashtags = diary.getDiaryHashtags();
        for(DiaryHashtag diaryHashtag : diaryHashtags) {
            String diaryHashtagString = diaryHashtag.getHashtag().getName();
            diaryHashtagStrings.add(diaryHashtagString);
        }
        this.hashtags = diaryHashtagStrings;

        List<String> diaryImagePaths = new ArrayList<>();
        List<DiaryImage> diaryImages = diary.getDiaryImages();
        for (DiaryImage diaryImage : diaryImages) {
            diaryImagePaths.add(diaryImage.getPath());
        }
        this.diaryImages = diaryImagePaths;

        this.placeName = diary.getDiaryPlace().getPlace().getName();


    }
}

