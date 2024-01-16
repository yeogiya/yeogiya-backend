package com.yeogiya.web.diary.service;

import com.yeogiya.entity.diary.*;
import com.yeogiya.entity.member.Member;
import com.yeogiya.enumerable.EnumErrorCode;
import com.yeogiya.exception.ClientException;
import com.yeogiya.repository.*;
import com.yeogiya.web.auth.PrincipalDetails;
import com.yeogiya.web.diary.dto.request.CalendarPageRequestDTO;
import com.yeogiya.web.diary.dto.request.DiaryModifyRequestDTO;
import com.yeogiya.web.diary.dto.request.DiarySaveRequestDTO;
import com.yeogiya.web.diary.dto.response.CalendarPageResponseDTO;
import com.yeogiya.web.diary.dto.response.DiariesResponseDTO;
import com.yeogiya.web.diary.dto.response.DiaryIdResponseDTO;
import com.yeogiya.web.diary.dto.request.PlaceRequestDTO;
import com.yeogiya.web.diary.dto.response.DiaryResponseDTO;
import com.yeogiya.web.place.PlaceService;
import com.yeogiya.web.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;
    private final DiaryPlaceService diaryPlaceService;
    private final PlaceService placeService;

    private final DiaryImageService diaryImageService;
    private final DiaryHashtagService diaryHashtagService;

    @Transactional
    public DiaryIdResponseDTO postDiary(DiarySaveRequestDTO diarySaveRequestDTO,
                                        PlaceRequestDTO placeRequestDTO,
                                        List<MultipartFile> multipartFiles,
                                        String memberId) throws IOException {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ClientException.NotFound(EnumErrorCode.NOT_FOUND_MEMBER));
        Diary diary = diarySaveRequestDTO.toEntity(member);
        diaryRepository.save(diary);

        diaryHashtagService.register(diarySaveRequestDTO.getHashtags(), diary);

        // 이미지 저장 로직
        String thumbnail = ObjectUtils.isEmpty(multipartFiles) ? "" : diaryImageService.registerThumbnail(multipartFiles.get(0));
        diary.addThumbnail(thumbnail);
        diaryImageService.register(multipartFiles, diary);

        // 장소 저장 로직
        Place place = placeRepository.findByKakaoId(placeRequestDTO.getKakaoId())
                .orElseGet(() -> placeRepository.save(placeRequestDTO.toPlace()));

        diaryPlaceService.register(diary, place);

        return DiaryIdResponseDTO.builder()
                .id(diary.getId())
                .build();
    }

    @Transactional
    public DiaryIdResponseDTO modifyDiary(Long diaryId,
                                          DiaryModifyRequestDTO diaryModifyRequestDTO,
                                          PlaceRequestDTO placeRequestDTO,
                                          List<MultipartFile> multipartFiles,
                                          String memberId) throws IOException {

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new ClientException.NotFound(EnumErrorCode.NOT_FOUND_DIARY));
        validateAccess(memberId, diary);

        // 태그
        diaryHashtagService.deleteByDiaryId(diaryId);
        diaryHashtagService.register(diaryModifyRequestDTO.getHashtags(), diary);

        String thumbnail = "";
        // 이미지 있을 때만 수정되도록 처리
        if (!ObjectUtils.isEmpty(multipartFiles)) {
            diaryImageService.deleteByDiaryId(diaryId);
            thumbnail = diaryImageService.registerThumbnail(multipartFiles.get(0));
            diaryImageService.register(multipartFiles, diary);
        }

        // 장소 저장 로직
        Place place = placeRepository.findByKakaoId(placeRequestDTO.getKakaoId())
                .orElseGet(() -> placeRepository.save(placeRequestDTO.toPlace()));

        DiaryPlace diaryPlace = diaryPlaceService.getDiaryPlaceByDiaryId(diaryId);
        diaryPlace.update(place);
        diary.update(diaryModifyRequestDTO.getContent(), diaryModifyRequestDTO.getOpenYn(), diaryModifyRequestDTO.getStar(), diaryModifyRequestDTO.getDate(), thumbnail);

        return DiaryIdResponseDTO.builder()
                .id(diaryId)
                .build();
    }

    @Transactional
    public DiaryIdResponseDTO deleteDiary(Long diaryId, String memberId) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
                () -> new ClientException.NotFound(EnumErrorCode.NOT_FOUND_DIARY)
        );
        validateAccess(memberId, diary);
        diaryRepository.delete(diary);

        return DiaryIdResponseDTO.builder()
                .id(diaryId)
                .build();
    }

    public DiaryResponseDTO getDiary(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() ->  new ClientException.NotFound(EnumErrorCode.NOT_FOUND_DIARY));
        DiaryResponseDTO diaryResponseDTO = new DiaryResponseDTO(diary);

        return diaryResponseDTO;
    }

    @Transactional
    public CalendarPageResponseDTO getDiaries(CalendarPageRequestDTO calendarPageRequestDTO, PrincipalDetails principal) {
        List<Diary> diaries = new ArrayList<>();

        // if(calendarPageRequestDTO.getDay()==0){  월별, 주간별 기능 추가되면 복구
            Calendar cal = Calendar.getInstance();
            cal.set(calendarPageRequestDTO.getYear(), calendarPageRequestDTO.getMonth(),calendarPageRequestDTO.getDay());
            calendarPageRequestDTO.setDay(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            LocalDate startDate = DateUtils.startDate(calendarPageRequestDTO);
            LocalDate endDate = DateUtils.getDate(calendarPageRequestDTO);
            diaries = diaryRepository.findAllByDateBetweenAndMemberOrderByDateAsc(
                    DateUtils.startDate(calendarPageRequestDTO)
                    ,DateUtils.getDate(calendarPageRequestDTO),principal.getMember());
        // }

        CalendarPageResponseDTO calendarPageResponseDTO = new CalendarPageResponseDTO();
        for(Diary diary : diaries) {
            calendarPageResponseDTO.getDiaries().add(new DiariesResponseDTO(diary));
        }
        calendarPageResponseDTO.setTotalCnt(diaries.size());

        return calendarPageResponseDTO;
    }

    private void validateAccess(String memberId, Diary diary) {
        if (!Objects.equals(memberId, diary.getMember().getId())) {
            throw new ClientException.Forbidden(EnumErrorCode.INVALID_ACCESS);
        }
    }

    public List<DiaryResponseDTO> getDiariesByPlaceKakaoId(Integer kakaoId) {
        List<Long> diaryIds = diaryPlaceService.getDiaryPlaceByPlaceId(placeService.getPlaceByKakaoId(kakaoId).getId()).stream()
                .map(diaryPlace -> diaryPlace.getDiary().getId())
                .collect(Collectors.toList());

        return diaryRepository.findAllByIdInAndOpenYnIsOrderByIdDesc(diaryIds, OpenYn.Y).stream()
                .map(DiaryResponseDTO::new)
                .collect(Collectors.toList());
    }
}
