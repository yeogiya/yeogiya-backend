package com.yeogiya.web.search.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yeogiya.web.search.dto.response.KakaoPlaceSearchResponseDTO;
import com.yeogiya.web.search.dto.response.SearchDetailsResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoSearchResponseDTO {

    private List<Document> documents;
    private Meta meta;

    public SearchDetailsResponseDTO.KakaoResult toKakaoResult(String lat, String lng) {
        List<SearchDetailsResponseDTO.KakaoResult> result = documents.stream()
                .filter(document -> document.isCorrect(lat, lng))
                .collect(Collectors.toList())
                .stream()
                .map(Document::toKakaoResult)
                .collect(Collectors.toList());

        if (ObjectUtils.isEmpty(result)) {
            return null;
        }

        return result.get(0);
    }

    public KakaoPlaceSearchResponseDTO toKakaoSearchResponseDTO() {
        return KakaoPlaceSearchResponseDTO.builder()
                .isEnd(meta.isEnd())
                .places(documents.stream()
                        .map(Document::toKakaoPlaceSearchResponseDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Meta {

        @JsonProperty("is_end")
        private boolean isEnd;

        @JsonProperty("pageable_count")
        private int pageableCount;

        @JsonProperty("same_name")
        private SameName sameName;

        @JsonProperty("total_count")
        private int totalCount;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class SameName {

            private String keyword;
            private List<String> region;

            @JsonProperty("selected_region")
            private String selectedRegion;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Document {

        @JsonProperty("address_name")
        private String addressName;

        @JsonProperty("category_group_name")
        private String categoryGroupName;

        @JsonProperty("category_name")
        private String categoryName;

        private String id;
        private String phone;

        @JsonProperty("place_name")
        private String placeName;

        @JsonProperty("place_url")
        private String placeUrl;

        @JsonProperty("road_address_name")
        private String roadAddressName;

        private String x;
        private String y;

        public SearchDetailsResponseDTO.KakaoResult toKakaoResult() {
            return SearchDetailsResponseDTO.KakaoResult.builder()
                    .name(placeName)
                    .roadAddress(roadAddressName)
                    .address(addressName)
                    .phone(phone)
                    .build();
        }

        public boolean isCorrect(String googleLat, String googleLng) {
            return x.contains(googleLng) && y.contains(googleLat);
        }

        public KakaoPlaceSearchResponseDTO.Place toKakaoPlaceSearchResponseDTO() {
            return KakaoPlaceSearchResponseDTO.Place.builder()
                    .kakaoId(Integer.parseInt(id))
                    .name(placeName)
                    .address(addressName)
                    .lat(Double.parseDouble(y))
                    .lng(Double.parseDouble(x))
                    .build();
        }
    }
}
