package com.yeogiya.repository;

import com.yeogiya.entity.diary.Hashtag;
import com.yeogiya.entity.diary.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    Optional<Place> findByKakaoId(int kakaoId);
}
