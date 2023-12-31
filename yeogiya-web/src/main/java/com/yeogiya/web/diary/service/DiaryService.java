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
import com.yeogiya.web.image.ImageUploadService;
import com.yeogiya.web.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final HashtagRepository hashtagRepository;
    private final DiaryHashtagRepository diaryHashtagRepository;
    private final MemberRepository memberRepository;
    private final DiaryPlaceRepository diaryPlaceRepository;
    private final PlaceRepository placeRepository;
    private final DiaryImageRepository diaryImageRepository;

    private final DiaryImageService diaryImageService;

    private final ImageUploadService imageUploadService;

    @Transactional
    public DiaryIdResponseDTO postDiary(DiarySaveRequestDTO diarySaveRequestDTO,
                                        PlaceRequestDTO placeRequestDTO,
                                        PrincipalDetails principal,
                                        List<MultipartFile> multipartFiles) throws IOException {

        Member member = memberRepository.findById(principal.getUsername()).orElseThrow(() -> new ClientException.NotFound(EnumErrorCode.NOT_FOUND_MEMBER));
        Diary diary = diarySaveRequestDTO.toEntity(member);

        List<DiaryHashtag> diaryHashtags = new ArrayList<>();
        List<String> tagStrings = diarySaveRequestDTO.getHashtags();

        if(tagStrings.size() != 0) {
            tagStrings.stream()
                    .map(hashtag ->
                            hashtagRepository.findByName(hashtag)
                                    .orElseGet(() -> hashtagRepository.save(
                                            Hashtag.builder()
                                                    .name(hashtag)
                                                    .build())))
                    .forEach(hashtag -> {
                            DiaryHashtag diaryHashtag = new DiaryHashtag(diary, hashtag);
                            diaryHashtags.add(diaryHashtag);
                            diaryHashtagRepository.save(diaryHashtag);
                    });
        }

        // 이미지 저장 로직
        List<DiaryImage> diaryImages = new ArrayList<>();
        String thumbPath = "";
        if (multipartFiles != null){
           thumbPath = imageUploadService.uploadThumb(multipartFiles.get(0));
            diary.addThumbnail(thumbPath);
            for (MultipartFile m : multipartFiles) {
                DiaryImage imageFileUpload = diaryImageService.upload(m, diary);
                diaryImages.add(imageFileUpload);
            }
        }

        // 장소 저장 로직
        int kakaoId = placeRequestDTO.getKakaoId();
        placeRepository.findByKakaoId(kakaoId).orElseGet(() -> placeRepository.save(
                Place.builder()
                .name(placeRequestDTO.getName())
                .address(placeRequestDTO.getAddress())
                .kakaoId(placeRequestDTO.getKakaoId())
                .longitude(placeRequestDTO.getLongitude())
                .latitude(placeRequestDTO.getLatitude())
                .build()));

        Place place = placeRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new ClientException.NotFound(EnumErrorCode.NOT_FOUND_PLACE));
        DiaryPlace diaryPlace = new DiaryPlace(diary, place);
        diaryPlaceRepository.save(diaryPlace);



        diaryRepository.save(diary);


        return DiaryIdResponseDTO.builder()
                .id(diary.getId())
                .build();
    }

    @Transactional
    public DiaryIdResponseDTO modifyDiary(Long diaryId,
                                          DiaryModifyRequestDTO diaryModifyRequestDTO,
                                          PlaceRequestDTO placeRequestDTO,
                                          PrincipalDetails principal,
                                          List<MultipartFile> multipartFiles) throws IOException {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
                () -> new ClientException.NotFound(EnumErrorCode.NOT_FOUND_DIARY)
        );

        validateAccess(principal, diary);
        // 태그
        diaryHashtagRepository.deleteByDiaryId(diaryId);
        List<String> tagStrings = diaryModifyRequestDTO.getHashtags();
        if (tagStrings.size() != 0) {
            tagStrings.stream()
                    .map(hashtag ->
                            hashtagRepository.findByName(hashtag)
                                    .orElseGet(() -> hashtagRepository.save(
                                            Hashtag.builder()
                                                    .name(hashtag)
                                                    .build())))
                    .forEach(hashtag -> {
                        DiaryHashtag diaryHashtag = new DiaryHashtag(diary, hashtag);
                        diaryHashtagRepository.save(diaryHashtag);
                    });
        }


        // 이미지
        diaryImageRepository.deleteByDiaryId(diaryId);
        String thumbPath = "";
        if (multipartFiles != null) {
            thumbPath = imageUploadService.uploadThumb(multipartFiles.get(0));
        }
        for (MultipartFile m : multipartFiles) {
            DiaryImage imageFileUpload = diaryImageService.upload(m, diary);
        }

        // 장소
        int kakaoId = placeRequestDTO.getKakaoId();
        placeRepository.findByKakaoId(kakaoId).orElseGet(() -> placeRepository.save(
                Place.builder()
                        .name(placeRequestDTO.getName())
                        .address(placeRequestDTO.getAddress())
                        .kakaoId(placeRequestDTO.getKakaoId())
                        .longitude(placeRequestDTO.getLongitude())
                        .latitude(placeRequestDTO.getLatitude())
                        .build()));

        Place place = placeRepository.findByKakaoId(kakaoId).orElseThrow(() -> new ClientException.NotFound(EnumErrorCode.NOT_FOUND_PLACE));
        DiaryPlace diaryPlace = diaryPlaceRepository.findByDiaryId(diaryId).orElseThrow(() -> new ClientException.NotFound(EnumErrorCode.NOT_FOUND_PLACE));
        diaryPlace.update(place);


        diary.update(diaryModifyRequestDTO.getContent(), diaryModifyRequestDTO.getOpenYn(), diaryModifyRequestDTO.getStar(), diaryModifyRequestDTO.getDate(), thumbPath);


        return DiaryIdResponseDTO.builder()
                .id(diaryId)
                .build();

    }

    @Transactional
    public DiaryIdResponseDTO deleteDiary(Long diaryId, PrincipalDetails principal) {

        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
                () -> new ClientException.NotFound(EnumErrorCode.NOT_FOUND_DIARY)
        );
        validateAccess(principal, diary);
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

    private void validateAccess(PrincipalDetails principal, Diary diary) {
        if (principal.getMember().getMemberId() != diary.getMember().getMemberId()) {
            throw new ClientException.Forbidden(EnumErrorCode.INVALID_ACCESS);
        }
    }

}
