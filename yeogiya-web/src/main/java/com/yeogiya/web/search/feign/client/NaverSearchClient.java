package com.yeogiya.web.search.feign.client;

import com.yeogiya.web.search.feign.dto.NaverLocalSearchResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naverSearchUrl", url = "${search.naver.url}")
public interface NaverSearchClient {

    @GetMapping
    NaverLocalSearchResponseDTO searchPlaceByKeyword(
            @RequestHeader(name = "X-Naver-Client-Id") String XNaverClientID,
            @RequestHeader(name = "X-Naver-Client-Secret") String XNaverClientSecret,
            @RequestParam String query,
            @RequestParam(required = false) int display);
}
