package com.yeogiya.web.member.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberValidationResponseDTO {

    private boolean isDuplicated;
}
