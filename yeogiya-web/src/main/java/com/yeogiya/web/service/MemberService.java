package com.yeogiya.web.service;

import com.yeogiya.entity.member.Member;
import com.yeogiya.repository.MemberRepository;
import com.yeogiya.web.dto.JoinRequestDto;
import com.yeogiya.web.dto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Long join(JoinRequestDto requestDto) {
        String rawPassword = requestDto.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        requestDto.setPassword(encPassword);
        return memberRepository.save(requestDto.toEntity()).getMemberId();
    }

    public String login(LoginRequestDto requestDto) {
        String id = requestDto.getId();
        String rawPassword = requestDto.getPassword();

        Member member = memberRepository.findById(id);

        // 비밀번호 일치 여부 확인
        if(passwordEncoder.matches(rawPassword, member.getPassword())){
            return "로그인 성공";
        }

        return "로그인 실패";
    }
}
