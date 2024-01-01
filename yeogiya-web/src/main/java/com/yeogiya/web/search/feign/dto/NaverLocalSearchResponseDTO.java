package com.yeogiya.web.search.feign.dto;

import com.yeogiya.web.search.dto.response.SearchDetailsResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NaverLocalSearchResponseDTO {

    private List<Item> items;

    public SearchDetailsResponseDTO.NaverResult toNaverResult(String lat, String lng) {
        List<SearchDetailsResponseDTO.NaverResult> result = items.stream()
                .filter(item -> item.isCorrect(lat, lng))
                .collect(Collectors.toList())
                .stream()
                .map(Item::toNaverResult)
                .collect(Collectors.toList());

        if (ObjectUtils.isEmpty(result)) {
            return null;
        }

        return result.get(0);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private String title;
        private String link;
        private String category;
        private String description;
        private String telephone;
        private String address;
        private String roadAddress;
        private String mapx;
        private String mapy;

        public boolean isCorrect(String lat, String lng) {
            return mapx.contains(lng) && mapy.contains(lat);
        }

        public SearchDetailsResponseDTO.NaverResult toNaverResult() {
            return SearchDetailsResponseDTO.NaverResult.builder()
                    .name(removeHtmlTags(title))
                    .roadAddress(roadAddress)
                    .address(address)
                    .category(category)
                    .build();
        }

        private String removeHtmlTags(String param) {
            return param.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
        }
    }
}
