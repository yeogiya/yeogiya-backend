package com.yeogiya.web.search.service;

import com.yeogiya.web.search.dto.response.PlaceSearchResponseDTO;
import com.yeogiya.web.search.feign.client.GoogleSearchClient;
import com.yeogiya.web.search.feign.dto.GoogleTextSearchResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class SearchService {

    private final GoogleSearchClient googleSearchClient;

    @Value("${search.kakao.api-key}")
    private String kakaoApiKey;

    @Value("${search.google.api-key}")
    private String googleApiKey;

    public PlaceSearchResponseDTO searchPlacesByKeyword(String query, String language, String pageToken) {
        GoogleTextSearchResponseDTO googleTextSearchResponseDTO = googleSearchClient.textSearch(googleApiKey, query, language, pageToken);

        return PlaceSearchResponseDTO.builder()
                .pageToken(googleTextSearchResponseDTO.getNextPageToken())
                .places(googleTextSearchResponseDTO.getResults().stream()
                        .map(GoogleTextSearchResponseDTO.Result::toPlaceInfo)
                        .collect(toList()))
                .build();
    }
}
