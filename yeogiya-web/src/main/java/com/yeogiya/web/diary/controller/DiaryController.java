package com.yeogiya.web.diary.controller;

import com.yeogiya.dto.response.CommonResponse;
import com.yeogiya.web.auth.PrincipalDetails;
import com.yeogiya.web.diary.dto.request.CalendarPageRequestDTO;
import com.yeogiya.web.diary.dto.request.DiaryModifyRequestDTO;
import com.yeogiya.web.diary.dto.request.DiarySaveRequestDTO;
import com.yeogiya.web.diary.dto.response.CalendarPageResponseDTO;
import com.yeogiya.web.diary.dto.response.DiaryIdResponseDTO;
import com.yeogiya.web.diary.dto.request.PlaceRequestDTO;
import com.yeogiya.web.diary.dto.response.DiaryResponseDTO;
import com.yeogiya.web.diary.service.DiaryService;
import com.yeogiya.web.diary.swagger.DiarySwagger;
import com.yeogiya.web.util.MemberUtil;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class DiaryController implements DiarySwagger {

    private final DiaryService diaryService;

    @PostMapping(value = "/auth/v1.0.0/diaries", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResponse<DiaryIdResponseDTO> postDiary(@RequestPart(name = "diary") @Valid DiarySaveRequestDTO diary,
                                                        @RequestPart(name = "place") PlaceRequestDTO place,
                                                        @RequestPart(name = "diaryImage", required = false) List<MultipartFile> multipartFiles,
                                                        @Parameter(hidden = true) Principal principal) throws IOException {

        return new CommonResponse<>(HttpStatus.OK, diaryService.postDiary(diary, place, multipartFiles, MemberUtil.getMemberId(principal)));
    }

    @PatchMapping(value = "/auth/v1.0.0/diaries/{diaryId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResponse<DiaryIdResponseDTO> modifyDiary (@PathVariable Long diaryId,
                                                           @RequestPart(name = "diary") DiaryModifyRequestDTO diary,
                                                           @RequestPart(name = "place") PlaceRequestDTO place,
                                                           @RequestPart(name = "diaryImage", required = false)
                                                                       List<MultipartFile> multipartFiles,
                                                           @Parameter(hidden = true) Principal principal) throws IOException {

        return new CommonResponse<>(HttpStatus.OK, diaryService.modifyDiary(diaryId, diary, place, multipartFiles, MemberUtil.getMemberId(principal)));
    }

    @DeleteMapping("/auth/v1.0.0/diaries/{diaryId}")
    public CommonResponse<DiaryIdResponseDTO> deleteDiary (@PathVariable Long diaryId, @Parameter(hidden = true) Principal principal) {
        return new CommonResponse<>(HttpStatus.OK, diaryService.deleteDiary(diaryId, MemberUtil.getMemberId(principal)));
    }

    @GetMapping("/auth/v1.0.0/diaries/{diaryId}")
    public CommonResponse<DiaryResponseDTO> getDiary(@PathVariable Long diaryId, @Parameter(hidden = true) @AuthenticationPrincipal PrincipalDetails principal) {
        return new CommonResponse<>(HttpStatus.OK, diaryService.getDiary(diaryId));
    }

    @GetMapping("/auth/v1.0.0/diaries")
    public CommonResponse<CalendarPageResponseDTO> getDiaries(CalendarPageRequestDTO calendarPageRequestDTO,
                                                              @Parameter(hidden = true) @AuthenticationPrincipal PrincipalDetails principal) {
         return new CommonResponse<>(HttpStatus.OK, diaryService.getDiaries(calendarPageRequestDTO, principal));
    }


}
