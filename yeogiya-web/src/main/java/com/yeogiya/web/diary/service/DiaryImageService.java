package com.yeogiya.web.diary.service;


import com.yeogiya.entity.diary.Diary;
import com.yeogiya.entity.diary.DiaryImage;
import com.yeogiya.repository.DiaryImageRepository;
import com.yeogiya.web.diary.dto.request.DiaryImageRequestDTO;
import com.yeogiya.web.image.ImageUploadService;
import com.yeogiya.web.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryImageService {

    private final DiaryImageRepository diaryImageRepository;
    private final S3Uploader s3Uploader;

    private final ImageUploadService imageUploadService;

    private DiaryImage upload(MultipartFile multipartFile, Diary diary) throws IOException {
        String originalName = multipartFile.getOriginalFilename();
        String savedName = s3Uploader.createFileName(originalName);
        String path = imageUploadService.upload(multipartFile);

        DiaryImageRequestDTO diaryImageRequestDto = new DiaryImageRequestDTO(originalName, savedName, path);

        return diaryImageRepository.save(diaryImageRequestDto.toEntity(diary));
    }

    public DiaryImage getByDiaryId(List<Long> diaryIds) {
        List<DiaryImage> diaryImages = diaryImageRepository.findByDiaryIdInOrderByIdDesc(diaryIds);

        if (ObjectUtils.isEmpty(diaryImages)) {
            return null;
        }

        return diaryImages.get(0);
    }

    public void register(List<MultipartFile> multipartFiles, Diary diary) throws IOException {
        if (ObjectUtils.isEmpty(multipartFiles)) {
            return;
        }

        String thumbPath = "";
        thumbPath = imageUploadService.uploadThumbnail(multipartFiles.get(0));
        diary.addThumbnail(thumbPath);
        for (MultipartFile m : multipartFiles) {
            upload(m, diary);
        }
    }

    public String registerThumbnail(MultipartFile multipartFile) {
        return imageUploadService.uploadThumbnail(multipartFile);
    }

    @Transactional
    public void deleteByDiaryId(Long diaryId) {
        diaryImageRepository.deleteByDiaryId(diaryId);
    }
}
