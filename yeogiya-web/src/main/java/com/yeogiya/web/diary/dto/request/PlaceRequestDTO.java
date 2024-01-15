package com.yeogiya.web.diary.dto.request;

import com.yeogiya.entity.diary.Place;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PlaceRequestDTO {
    private String name;
    private String address;
    private int kakaoId;
    private Double latitude;
    private Double longitude;

    public Place toPlace() {
        return Place.builder()
                .name(name)
                .address(address)
                .kakaoId(kakaoId)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
