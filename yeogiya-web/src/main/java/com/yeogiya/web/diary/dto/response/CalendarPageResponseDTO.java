package com.yeogiya.web.diary.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarPageResponseDTO {
    private long totalCnt;
    private List<DiariesResponseDTO> diaries = new ArrayList<>();
}
