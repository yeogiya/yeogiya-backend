package com.yeogiya.web.search.controller;

import com.yeogiya.dto.response.CommonResponse;
import com.yeogiya.web.search.dto.response.PlaceSearchResponseDTO;
import com.yeogiya.web.search.dto.response.SearchDetailsResponseDTO;
import com.yeogiya.web.search.service.SearchService;
import com.yeogiya.web.search.swagger.SearchSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/public/v1.0.0/search")
public class SearchController implements SearchSwagger {

    private final SearchService searchService;

    @GetMapping("/places")
    public CommonResponse<PlaceSearchResponseDTO> searchPlaceByKeyword(@RequestParam String keyword,
                                                                       @RequestParam(required = false) String pageToken) {

        PlaceSearchResponseDTO responseDTO = searchService.searchPlacesByKeyword(keyword, pageToken);

        return new CommonResponse<>(HttpStatus.OK, responseDTO);
    }

    @GetMapping("/places/details")
    public CommonResponse<SearchDetailsResponseDTO> searchDetails(@RequestParam String placeId,
                                                                  @RequestParam String keyword) {

        SearchDetailsResponseDTO responseDTO = searchService.searchPlaceDetails(placeId, "ko", keyword);

        return new CommonResponse<>(HttpStatus.OK, responseDTO);
    }
}
