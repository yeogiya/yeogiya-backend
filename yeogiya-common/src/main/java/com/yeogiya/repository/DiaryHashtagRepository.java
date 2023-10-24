package com.yeogiya.repository;

import com.yeogiya.entity.diary.DiaryHashtag;
import com.yeogiya.entity.diary.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryHashtagRepository extends JpaRepository<DiaryHashtag, Long> {
    Long deleteByDiaryId(Long diaryId);
}
