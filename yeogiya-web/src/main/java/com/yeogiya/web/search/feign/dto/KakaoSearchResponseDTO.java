package com.yeogiya.web.search.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoSearchResponseDTO {

    private List<Document> documents;
    private Meta meta;

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
    }
}
