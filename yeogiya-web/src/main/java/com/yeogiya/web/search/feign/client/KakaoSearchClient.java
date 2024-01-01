package com.yeogiya.web.search.feign.client;

import com.yeogiya.web.search.feign.dto.KakaoSearchResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoSearchUrl", url = "${search.kakao.url}")
public interface KakaoSearchClient {

    @GetMapping
    KakaoSearchResponseDTO searchPlaceByKeyword(
            @RequestHeader String Authorization,
            @RequestParam String query,
            @RequestParam Double x,
            @RequestParam Double y,
            @RequestParam int radius);
}
