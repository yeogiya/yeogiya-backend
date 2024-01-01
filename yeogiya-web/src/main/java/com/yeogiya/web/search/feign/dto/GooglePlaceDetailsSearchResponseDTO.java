package com.yeogiya.web.search.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yeogiya.web.search.dto.response.SearchDetailsResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GooglePlaceDetailsSearchResponseDTO {

    private String status;
    private Result result;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {

        @JsonProperty("formatted_address")
        private String formattedAddress;

        @JsonProperty("formatted_phone_number")
        private String formattedPhoneNumber;

        private String name;

        @JsonProperty("current_opening_hours")
        private CurrentOpeningHours currentOpeningHours;

        private Double rating;

        private Geometry geometry;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class CurrentOpeningHours {

            @JsonProperty("weekday_text")
            private List<String> weekdayText;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Geometry {

            private Location location;

            public Double getLat() {
                return location.getLat();
            }

            public Double getLng() {
                return location.getLng();
            }

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            private static class Location {
                private Double lat;
                private Double lng;
            }
        }

        public SearchDetailsResponseDTO.GoogleResult toGoogleResult() {
            return SearchDetailsResponseDTO.GoogleResult.builder()
                    .name(name)
                    .roadAddress(formattedAddress.replace("대한민국 ", ""))
                    .address(formattedAddress.replace("대한민국 ", ""))
                    .operatingHours(currentOpeningHours == null || currentOpeningHours.getWeekdayText() == null ? null : currentOpeningHours.getWeekdayText())
                    .phone(formattedPhoneNumber)
                    .build();
        }
    }
}
