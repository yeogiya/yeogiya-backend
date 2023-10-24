package com.yeogiya.web.diary.dto.request;

import com.yeogiya.entity.diary.Diary;
import com.yeogiya.entity.diary.DiaryImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DiaryImageRequestDTO {
    private String orgName;
    private String savedName;
    private String path;


    public DiaryImage toEntity(Diary diary) {
        return DiaryImage.builder()
                .orgName(this.orgName)
                .savedName(this.savedName)
                .path(this.path)
                .diary(diary)
                .build();
    }
}
