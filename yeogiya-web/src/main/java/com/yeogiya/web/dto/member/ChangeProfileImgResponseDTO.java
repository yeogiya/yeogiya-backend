package com.yeogiya.web.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "프로필 이미지 변경 응답 객체")
@Getter
@Builder
@AllArgsConstructor
public class ChangeProfileImgResponseDTO {

    @Schema(description = "변경된 프로필 이미지 URL")
    private final String profileImgUrl;
}
