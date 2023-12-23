package com.yeogiya.web.search.feign.client;

import com.yeogiya.web.search.feign.dto.GoogleTextSearchResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "googleSearchUrl", url = "${search.google.url}")
public interface GoogleSearchClient {

    @GetMapping("/textsearch/json")
    GoogleTextSearchResponseDTO textSearch(
            @RequestParam String key,
            @RequestParam String query,
            @RequestParam String language,
            @RequestParam(required = false, name = "pagetoken") String pageToken
    );
}
