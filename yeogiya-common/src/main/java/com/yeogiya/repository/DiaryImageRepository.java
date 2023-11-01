package com.yeogiya.repository;

import com.yeogiya.entity.diary.DiaryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryImageRepository extends JpaRepository<DiaryImage, Long> {
    Long deleteByDiaryId(Long diaryId);

}
