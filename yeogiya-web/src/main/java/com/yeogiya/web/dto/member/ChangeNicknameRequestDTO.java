package com.yeogiya.web.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Schema(name = "닉네임 변경 요청 객체")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeNicknameRequestDTO {

    @Schema(description = "변경할 닉네임")
    @NotEmpty
    private String nickname;
}
