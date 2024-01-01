package com.yeogiya.web.search.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "장소 상세 검색 응답 DTO")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchDetailsResponseDTO {

    @Schema(description = "위도(3x.xxx)")
    private Double lat;

    @Schema(description = "경도(12x.xxx)")
    private Double lng;

    @Schema(description = "카테고리")
    private String category;

    @Schema(description = "구글 검색 결과")
    private GoogleResult google;

    @Schema(description = "네이버 검색 결과")
    private NaverResult naver;

    @Schema(description = "카카오 검색 결과")
    private KakaoResult kakao;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GoogleResult {

        private String name;
        private String roadAddress;
        private String address;
        private List<String> operatingHours;
        private String phone;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NaverResult {

        private String name;
        private String roadAddress;
        private String address;
        private String category;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoResult {
        private String name;
        private String roadAddress;
        private String address;
        private String phone;
    }
}
