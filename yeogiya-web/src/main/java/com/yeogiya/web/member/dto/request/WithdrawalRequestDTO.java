package com.yeogiya.web.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class WithdrawalRequestDTO {

    @Schema(description = "회원 탈퇴 사유")
    private String withdrawalReason;

    @Schema(description = "회원 탈퇴 사유 상세")
    private String withdrawalReasonDetail;
}
