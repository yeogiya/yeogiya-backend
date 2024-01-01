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
@Schema(name = "장소 검색 응답 DTO")
public class PlaceSearchResponseDTO {

    @Schema(description = "다음 페이지 토큰 / 페이지네이션 시 API 요청에 사용")
    private String pageToken;

    @Schema(description = "장소 정보 리스트")
    private List<PlaceInfo> places;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema
    public static class PlaceInfo {

        @Schema(description = "장소 이름")
        private String placeName;

        @Schema(description = "장소 주소")
        private String address;

        @Schema(description = "구글 평점")
        private Double googleRating;

        @Schema(description = "구글 장소 ID / 상세 정보 조회 시 사용")
        private String googlePlaceId;

        @Schema(description = "구글 이미지 검색 시 photo_reference로 사용")
        private String photoReference;

        @Schema(description = "여기야 평점")
        private Double yeogiyaRating;
    }

}
