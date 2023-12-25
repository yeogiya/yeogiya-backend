package com.yeogiya.web.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "회원 정보 수정 응답 객체")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyMemberInfoResponseDTO {

    @Schema(description = "변경된 닉네임")
    private String nickname;

    @Schema(description = "변경된 프로필 이미지 URL")
    private String profileImgUrl;
}
