package com.yeogiya.web.diary.service;

import com.yeogiya.entity.diary.Diary;
import com.yeogiya.entity.diary.DiaryPlace;
import com.yeogiya.entity.diary.Place;

import com.yeogiya.enumerable.EnumErrorCode;
import com.yeogiya.exception.ClientException;
import com.yeogiya.repository.DiaryPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DiaryPlaceService {

    private final DiaryPlaceRepository diaryPlaceRepository;

    public Double getAvgRating(Place place) {
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

    public List<DiaryPlace> getDiaryPlaceByPlaceId(Long placeId) {
    	return diaryPlaceRepository.findAllByPlaceId(placeId);
    }

    @Transactional
    public void register(Diary diary, Place place) {
        DiaryPlace diaryPlace = DiaryPlace.builder()
                .diary(diary)
                .place(place)
                .build();

        diaryPlaceRepository.save(diaryPlace);
    }

    public DiaryPlace getDiaryPlaceByDiaryId(Long diaryId) {
    	return diaryPlaceRepository.findByDiaryId(diaryId)
                .orElseThrow(() -> new ClientException.NotFound(EnumErrorCode.NOT_FOUND_PLACE));
    }
}
