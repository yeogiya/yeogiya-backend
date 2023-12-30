package com.yeogiya.web.search.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yeogiya.web.search.dto.response.PlaceSearchResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoogleTextSearchResponseDTO {

    private String status;

    @JsonProperty("next_page_token")
    private String nextPageToken;

    private List<Result> results;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {

        @JsonProperty("formatted_address")
        private String formattedAddress;

        private String name;

        @JsonProperty("place_id")
        private String placeId;
        private List<Photo> photos;
        private Double rating;

        @JsonProperty("user_ratings_total")
        private int userRatingsTotal;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Photo {

            @JsonProperty("photo_reference")
            private String photoReference;
        }

        public PlaceSearchResponseDTO.PlaceInfo toPlaceInfo() {
            String photoReference = ObjectUtils.isEmpty(photos) ? null : photos.get(0).getPhotoReference();

            return PlaceSearchResponseDTO.PlaceInfo.builder()
                    .address(formattedAddress.replace("대한민국 ", ""))
                    .placeName(name)
                    .googleRating(rating)
                    .googlePlaceId(placeId)
                    .photoReference(photoReference)
                    .build();
        }
    }
}
