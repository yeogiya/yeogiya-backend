package com.yeogiya.web.search.service;

import com.yeogiya.entity.diary.DiaryImage;
import com.yeogiya.entity.diary.Place;
import com.yeogiya.web.diary.dto.response.DiaryResponseDTO;
import com.yeogiya.web.diary.service.DiaryImageService;
import com.yeogiya.web.diary.service.DiaryPlaceService;
import com.yeogiya.web.diary.service.DiaryService;
import com.yeogiya.web.place.PlaceService;
import com.yeogiya.web.search.dto.response.KakaoPlaceSearchResponseDTO;
import com.yeogiya.web.search.dto.response.PlaceSearchResponseDTO;
import com.yeogiya.web.search.dto.response.SearchDetailsResponseDTO;
import com.yeogiya.web.search.feign.client.GoogleSearchClient;
import com.yeogiya.web.search.feign.client.KakaoSearchClient;
import com.yeogiya.web.search.feign.client.NaverSearchClient;
import com.yeogiya.web.search.feign.dto.GooglePlaceDetailsSearchResponseDTO;
import com.yeogiya.web.search.feign.dto.GoogleTextSearchResponseDTO;
import com.yeogiya.web.search.feign.dto.KakaoSearchResponseDTO;
import com.yeogiya.web.search.feign.dto.NaverLocalSearchResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class SearchService {

    private final PlaceService placeService;
    private final DiaryPlaceService diaryPlaceService;
    private final GoogleSearchClient googleSearchClient;
    private final KakaoSearchClient kakaoSearchClient;
    private final NaverSearchClient naverSearchClient;
    private final DiaryImageService diaryImageService;
    private final DiaryService diaryService;

    @Value("${search.kakao.api-key}")
    private String kakaoApiKey;

    @Value("${search.naver.client-id}")
    private String naverClientId;

    @Value("${search.naver.client-secret}")
    private String naverClientSecret;

    @Value("${search.google.api-key}")
    private String googleApiKey;

    public PlaceSearchResponseDTO searchPlacesByKeyword(String query, String pageToken) {
        GoogleTextSearchResponseDTO googleTextSearchResponseDTO = googleSearchClient.textSearch(googleApiKey, query, "ko", pageToken);

        List<PlaceSearchResponseDTO.PlaceInfo> places = googleTextSearchResponseDTO.getResults().stream()
                .map(result -> result.toPlaceInfo(diaryPlaceService.getAvgRating(placeService.getPlaceByAddressAndName(result.getFormattedAddress(), result.getName()))))
                .collect(toList());

        return PlaceSearchResponseDTO.builder()
                .pageToken(googleTextSearchResponseDTO.getNextPageToken())
                .places(places)
                .build();
    }

    public SearchDetailsResponseDTO searchPlaceDetails(String placeId, String language, String keyword) {
        GooglePlaceDetailsSearchResponseDTO googlePlaceDetailsSearchResponseDTO = googleSearchClient.placeDetails(googleApiKey, placeId, language);

        Double lat = googlePlaceDetailsSearchResponseDTO.getResult().getGeometry().getLat();
        Double lng = googlePlaceDetailsSearchResponseDTO.getResult().getGeometry().getLng();
        SearchDetailsResponseDTO.KakaoResult kakaoResult = getKakaoResult(lat, lng, keyword);
        SearchDetailsResponseDTO.NaverResult naverResult = getNaverResult(lat, lng, keyword);
        SearchDetailsResponseDTO.GoogleResult googleResult = googlePlaceDetailsSearchResponseDTO.getResult().toGoogleResult();

        String category = null;
        if (!ObjectUtils.isEmpty(kakaoResult) && kakaoResult.getCategory() != null) {
            category = kakaoResult.getCategory();
        } else if (!ObjectUtils.isEmpty(naverResult) && naverResult.getCategory() != null) {
            category = naverResult.getCategory();
        }

        List<DiaryResponseDTO> diaries = diaryService.getDiariesByPlaceKakaoId(kakaoResult.getKakaoId());

        return SearchDetailsResponseDTO.builder()
                .lat(lat)
                .lng(lng)
                .google(googleResult)
                .naver(naverResult)
                .kakao(kakaoResult)
                .category(category)
                .diaries(diaries)
                .build();
    }

    private SearchDetailsResponseDTO.NaverResult getNaverResult(Double googleLat, Double googleLng, String keyword) {
        String lat = String.valueOf(googleLat).replace(".", "").substring(0, 5);
        String lng = String.valueOf(googleLng).replace(".", "").substring(0, 6);
        NaverLocalSearchResponseDTO naverLocalSearchResponseDTO = naverSearchClient.searchPlaceByKeyword(naverClientId, naverClientSecret, keyword, 10);

        return naverLocalSearchResponseDTO.toNaverResult(lat, lng);
    }

    private SearchDetailsResponseDTO.KakaoResult getKakaoResult(Double googleLat, Double googleLng, String keyword) {
        String lat = String.valueOf(googleLat).substring(0, 5);
        String lng = String.valueOf(googleLng).substring(0, 6);

        KakaoSearchResponseDTO kakaoSearchResponseDTO = kakaoSearchClient.searchPlaceByKeyword(
                kakaoApiKey,
                keyword,
                googleLng,
                googleLat,
                100,
                1,
                15);

        return kakaoSearchResponseDTO.toKakaoResult(lat, lng);
    }

    public KakaoPlaceSearchResponseDTO searchByKakao(Double lat, Double lng, String keyword, Integer page, Integer size) {
        KakaoSearchResponseDTO kakaoSearchResponseDTO = kakaoSearchClient.searchPlaceByKeyword(
                kakaoApiKey,
                keyword,
                lng,
                lat,
                100,
                page,
                size);

        List<KakaoPlaceSearchResponseDTO.Place> places = kakaoSearchResponseDTO.getDocuments().stream()
                .map(document -> {
                    Place place = placeService.getPlaceByKakaoId(Integer.parseInt(document.getId()));

                    if (place == null) {
                        return document.toKakaoPlaceSearchResponseDTO(null, null);
                    }

                    List<Long> diaryIds = place.getDiaryPlaces().stream()
                            .map(diaryPlace -> diaryPlace.getDiary().getId())
                            .collect(toList());
                    DiaryImage diaryImage = diaryImageService.getByDiaryId(diaryIds);
                    String imageUrl = diaryImage == null ? null : diaryImage.getPath();
                    return document.toKakaoPlaceSearchResponseDTO(diaryPlaceService.getAvgRating(place), imageUrl);
                })
                .collect(toList());

        return KakaoPlaceSearchResponseDTO.builder()
                .isEnd(kakaoSearchResponseDTO.getMeta().isEnd())
                .places(places)
                .build();
    }
}
