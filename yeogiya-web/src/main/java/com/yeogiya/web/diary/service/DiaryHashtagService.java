package com.yeogiya.web.diary.service;

import com.yeogiya.entity.diary.Diary;
import com.yeogiya.entity.diary.DiaryHashtag;
import com.yeogiya.entity.diary.Hashtag;
import com.yeogiya.repository.DiaryHashtagRepository;
import com.yeogiya.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DiaryHashtagService {

    private final HashtagRepository hashtagRepository;
    private final DiaryHashtagRepository diaryHashtagRepository;

    public void register(List<String> hashtags, Diary diary) {
        if (ObjectUtils.isEmpty(hashtags)) {
            return;
        }

        hashtags.stream()
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

    @Transactional
    public void deleteByDiaryId(Long diaryId) {
        diaryHashtagRepository.deleteByDiaryId(diaryId);
    }
}
