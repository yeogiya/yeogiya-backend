package com.yeogiya.web.swagger;

import com.yeogiya.dto.response.CommonResponse;
import com.yeogiya.web.auth.PrincipalDetails;
import com.yeogiya.web.diary.dto.request.CalendarPageRequestDTO;
import com.yeogiya.web.diary.dto.request.DiaryModifyRequestDTO;
import com.yeogiya.web.diary.dto.request.DiarySaveRequestDTO;
import com.yeogiya.web.diary.dto.response.CalendarPageResponseDTO;
import com.yeogiya.web.diary.dto.response.DiaryIdResponseDTO;
import com.yeogiya.web.diary.dto.request.PlaceRequestDTO;
import com.yeogiya.web.diary.dto.response.DiaryResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "공간일기")
public interface DiarySwagger {

    @Operation(summary = "공간일기 쓰기")
    CommonResponse<DiaryIdResponseDTO> postDiary(DiarySaveRequestDTO diary, PlaceRequestDTO place, List<MultipartFile> multipartFiles, PrincipalDetails principal) throws IOException;

    @Operation(summary = "공간일기 수정")
    CommonResponse<DiaryIdResponseDTO> modifyDiary(Long diaryId, DiaryModifyRequestDTO diary, PlaceRequestDTO place, List<MultipartFile> multipartFiles, PrincipalDetails principal) throws IOException ;

    @Operation(summary = "공간일기 삭제")
    CommonResponse<DiaryIdResponseDTO> deleteDiary(Long diaryId, PrincipalDetails principal);

    @Operation(summary = "공간일기 뷰")
    CommonResponse<DiaryResponseDTO> getDiary(Long diaryId, PrincipalDetails principal);

    @Operation(summary = "공간일기 달력")
    CommonResponse<CalendarPageResponseDTO> getDiaries(CalendarPageRequestDTO calendarPageRequestDTO, PrincipalDetails principal);

}
