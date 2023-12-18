package com.yeogiya.repository;

import com.yeogiya.entity.diary.Diary;
import com.yeogiya.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findAllByDateBetweenAndMemberOrderByDateAsc(LocalDate start, LocalDate end, Member member);
}
