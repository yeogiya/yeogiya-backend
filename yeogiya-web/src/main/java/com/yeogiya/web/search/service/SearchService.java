package com.yeogiya.web.search.service;

import com.yeogiya.web.diary.service.DiaryPlaceService;
import com.yeogiya.web.search.dto.response.PlaceSearchResponseDTO;
import com.yeogiya.web.search.dto.response.SearchDetailsResponseDTO;
import com.yeogiya.web.search.feign.client.GoogleSearchClient;
import com.yeogiya.web.search.feign.dto.GooglePlaceDetailsSearchResponseDTO;
import com.yeogiya.web.search.feign.dto.GoogleTextSearchResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class SearchService {

    private final DiaryPlaceService diaryPlaceService;
    private final GoogleSearchClient googleSearchClient;

    @Value("${search.kakao.api-key}")
    private String kakaoApiKey;

    @Value("${search.google.api-key}")
    private String googleApiKey;

    public PlaceSearchResponseDTO searchPlacesByKeyword(String query, String language, String pageToken) {
        GoogleTextSearchResponseDTO googleTextSearchResponseDTO = googleSearchClient.textSearch(googleApiKey, query, language, pageToken);

        List<PlaceSearchResponseDTO.PlaceInfo> places = googleTextSearchResponseDTO.getResults().stream()
                .map(result -> result.toPlaceInfo(diaryPlaceService.getAvgRating(result.getFormattedAddress(), result.getName())))
                .collect(toList());

        return PlaceSearchResponseDTO.builder()
                .pageToken(googleTextSearchResponseDTO.getNextPageToken())
                .places(places)
                .build();
    }

    public SearchDetailsResponseDTO searchPlaceDetails(String placeId, String language, String keyword) {

        GooglePlaceDetailsSearchResponseDTO googlePlaceDetailsSearchResponseDTO = googleSearchClient.placeDetails(googleApiKey, placeId, language);

        return SearchDetailsResponseDTO.builder()
                .google(googlePlaceDetailsSearchResponseDTO.getResult().toGoogleResult())
                .build();
    }
}
