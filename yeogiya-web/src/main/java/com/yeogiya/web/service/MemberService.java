package com.yeogiya.web.service;

import com.yeogiya.repository.MemberRepository;
import com.yeogiya.web.dto.JoinRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(JoinRequestDto requestDto) {
        return memberRepository.save(requestDto.toEntity()).getMemberId();
    }
}
