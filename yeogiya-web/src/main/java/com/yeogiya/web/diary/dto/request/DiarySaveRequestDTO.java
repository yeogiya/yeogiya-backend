package com.yeogiya.web.diary.dto.request;

import com.yeogiya.entity.diary.Diary;
import com.yeogiya.entity.diary.DiaryImage;
import com.yeogiya.entity.diary.OpenYn;
import com.yeogiya.entity.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor
@Getter
public class DiarySaveRequestDTO {
    @NotBlank(message = "내용은 필수 입력 사항입니다.")
    private String content;
    private OpenYn openYn;

    private List<String> hashtags;

    public Diary toEntity(Member member) {
        return Diary.builder()
                .content(content)
                .openYn(openYn)
                .member(member)
                .build();
    }


}
