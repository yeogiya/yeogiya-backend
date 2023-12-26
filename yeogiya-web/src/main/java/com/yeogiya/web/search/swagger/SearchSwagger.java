package com.yeogiya.web.search.swagger;

import com.yeogiya.dto.response.CommonResponse;
import com.yeogiya.web.search.dto.response.PlaceSearchResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "검색", description = "검색 API")
public interface SearchSwagger {

    @Operation(summary = "장소 검색", description = "구글 API 사용 / 20개씩 응답")
    CommonResponse<PlaceSearchResponseDTO> searchPlaceByKeyword(@Parameter(in = ParameterIn.QUERY, required = true) String keyword,
                                                                @Parameter(in = ParameterIn.QUERY,
                                                                description = "다음 페이지 조회 시 required") String pageToken);
}