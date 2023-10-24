package com.yeogiya.web.swagger;

import com.yeogiya.dto.response.CommonResponse;
import com.yeogiya.web.diary.dto.request.DiarySaveRequestDTO;
import com.yeogiya.web.diary.dto.response.DiaryIdResponseDTO;
import com.yeogiya.web.diary.dto.request.PlaceRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "공간일기")
public interface DiarySwagger {

    @Operation(summary = "공간일기 쓰기")
    CommonResponse<DiaryIdResponseDTO> postDiary(DiarySaveRequestDTO diary, PlaceRequestDTO place, List<MultipartFile> multipartFiles);

}
