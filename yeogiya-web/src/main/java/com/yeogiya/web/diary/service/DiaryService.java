package com.yeogiya.web.diary.service;

import com.yeogiya.repository.DiaryRepository;
import com.yeogiya.web.diary.dto.DiarySaveRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;


}
