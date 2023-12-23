package com.yeogiya.web.search.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaverPlaceSearchResponseDTO {

    private String placeName;
    private String address;
    private String roadAddress;
    private String phone;
    private String category;
}
