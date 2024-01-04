package com.yeogiya.repository;

import com.yeogiya.entity.diary.DiaryPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryPlaceRepository extends JpaRepository<DiaryPlace, Long> {
    Optional<DiaryPlace> findByDiaryId(Long diaryID);

    List<DiaryPlace> findAllByPlaceId(Long placeId);
}
