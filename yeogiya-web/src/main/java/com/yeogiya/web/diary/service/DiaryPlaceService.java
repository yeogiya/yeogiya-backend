package com.yeogiya.web.diary.service;

import com.yeogiya.entity.diary.Diary;
import com.yeogiya.entity.diary.DiaryPlace;
import com.yeogiya.entity.diary.Place;
import com.yeogiya.web.place.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DiaryPlaceService {

    private final PlaceService placeService;

    public Double getAvgRating(String address, String name) {
        Place place = placeService.getPlaceByAddressAndName(address, name);
        if (place == null) {
            return null;
        }

        return Double.parseDouble(String.format("%.1f", place.getDiaryPlaces().stream()
                .map(DiaryPlace::getDiary)
                .map(Diary::getStar)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0)));
    }
}
