package com.yeogiya.web.diary.dto.request;

import com.yeogiya.entity.diary.OpenYn;
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


}
