package com.yeogiya.web.diary.controller;

import com.yeogiya.dto.response.CommonResponse;
import com.yeogiya.enumerable.EnumErrorCode;
import com.yeogiya.exception.ClientException;
import com.yeogiya.web.auth.PrincipalDetails;
import com.yeogiya.web.diary.dto.request.DiaryModifyRequestDTO;
import com.yeogiya.web.diary.dto.request.DiarySaveRequestDTO;
import com.yeogiya.web.diary.dto.response.DiaryIdResponseDTO;
import com.yeogiya.web.diary.dto.request.PlaceRequestDTO;
import com.yeogiya.web.diary.dto.response.DiaryResponseDTO;
import com.yeogiya.web.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping(value = "/auth/v1.0.0/diaries")
    public CommonResponse<DiaryIdResponseDTO> postDiary(@RequestPart("diary") DiarySaveRequestDTO diary,
                                                        @RequestPart("place") PlaceRequestDTO place,
                                                        @RequestPart(value = "diaryImage", required = false)
                                                            List<MultipartFile> multipartFiles,
                                                        @AuthenticationPrincipal PrincipalDetails principal) throws IOException, ParseException {
        if (principal != null) {
            return new CommonResponse<>(HttpStatus.OK, diaryService.postDiary(diary, place, principal, multipartFiles));
        } else
            throw new ClientException.Unauthorized(EnumErrorCode.AVAILABLE_AFTER_LOGGING_IN);

    }

    @PatchMapping("/auth/v1.0.0/diaries/{diaryId}")
    public CommonResponse<DiaryIdResponseDTO> modifyDiary (@PathVariable Long diaryId,
                                                           @RequestPart("diary") DiaryModifyRequestDTO diary,
                                                           @RequestPart("place") PlaceRequestDTO place,
                                                           @RequestPart(value = "diaryImage", required = false)
                                                                       List<MultipartFile> multipartFiles,
                                                           @AuthenticationPrincipal PrincipalDetails principal) throws IOException {

        if (principal != null) {
            return new CommonResponse<>(HttpStatus.OK, diaryService.modifyDiary(diaryId, diary, place, principal, multipartFiles));
        } else
            throw new ClientException.Unauthorized(EnumErrorCode.AVAILABLE_AFTER_LOGGING_IN);

    }

    @DeleteMapping("/auth/v1.0.0/diaries/{diaryId}")
    public CommonResponse<DiaryIdResponseDTO> deleteDiary (@PathVariable Long diaryId, @AuthenticationPrincipal PrincipalDetails principal) throws ParseException {
        if (principal != null)
            return new CommonResponse<>(HttpStatus.OK, diaryService.deleteDiary(diaryId, principal));
        else
            throw new ClientException.Unauthorized(EnumErrorCode.AVAILABLE_AFTER_LOGGING_IN);
    }

    @GetMapping("/auth/v1.0.0/diaries/{diaryId}")
    public CommonResponse<DiaryResponseDTO> getDiary(@PathVariable Long diaryId, @AuthenticationPrincipal PrincipalDetails principal) throws Exception {
        if (principal != null)
            return new CommonResponse<>(HttpStatus.OK, diaryService.getDiary(diaryId));
        else
            throw new ClientException.Unauthorized(EnumErrorCode.AVAILABLE_AFTER_LOGGING_IN);
    }


}
