package com.yeogiya.web.search.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchDetailsResponseDTO {

    private GoogleResult google;

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
        private Double lat;
        private Double lng;
    }
}
