package com.yeogiya.web.diary.dto.request;

import com.yeogiya.entity.diary.OpenYn;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
public class DiaryModifyRequestDTO {
    @NotBlank(message = "내용은 필수 입력 사항입니다.")
    private String content;
    private OpenYn openYn;
    private Double star;

    private LocalDate date;
    private List<String> hashtags;


}
