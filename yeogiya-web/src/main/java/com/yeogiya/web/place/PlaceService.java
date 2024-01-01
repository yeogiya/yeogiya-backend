package com.yeogiya.web.place;

import com.yeogiya.entity.diary.Place;
import com.yeogiya.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public Place getPlaceByAddressAndName(String address, String name) {
        return placeRepository.findByAddressContainsOrNameContains(address, name)
                .orElse(null);
    }

    public Place getPlaceByKakaoId(int kakaoId) {
        return placeRepository.findByKakaoId(kakaoId)
                .orElse(null);
    }
}
