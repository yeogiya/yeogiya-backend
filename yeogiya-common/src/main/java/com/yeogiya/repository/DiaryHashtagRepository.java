package com.yeogiya.repository;

import com.yeogiya.entity.diary.DiaryHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryHashtagRepository extends JpaRepository<DiaryHashtag, Long> {
    Long deleteByDiaryId(Long diaryId);
    List<DiaryHashtag> findByDiaryId(Long diaryID);
}
