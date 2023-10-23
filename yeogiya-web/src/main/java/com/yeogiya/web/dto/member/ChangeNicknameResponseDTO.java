package com.yeogiya.web.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "닉네임 변경 응답 객체")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeNicknameResponseDTO {

    @Schema(description = "변경된 닉네임")
    private String nickname;
}
