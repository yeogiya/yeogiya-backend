package com.yeogiya.web.search.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoPlaceSearchResponseDTO {

    @Schema(description = "true면 마지막 페이지입니다.")
    private boolean isEnd;

    @Schema(description = "장소 검색 결과")
    private List<Place> places;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Place {
        private int kakaoId;
        private String name;
        private String address;

        @Schema(description = "위도(3x.xxx)")
        private Double lat;

        @Schema(description = "경도(12x.xxx)")
        private Double lng;

        @Schema(description = "여기야 자체 평점")
        private Double yeogiyaRating;
    }
}
