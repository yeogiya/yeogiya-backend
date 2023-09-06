package com.yeogiya.web.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberValidationResponseDTO {

    private boolean isDuplicated;
}
