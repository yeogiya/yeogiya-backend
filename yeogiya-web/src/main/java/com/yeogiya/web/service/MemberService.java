package com.yeogiya.web.service;

import com.yeogiya.entity.member.Member;
import com.yeogiya.repository.MemberRepository;
import com.yeogiya.web.dto.JoinRequestDto;
import com.yeogiya.web.dto.LoginRequestDto;
import com.yeogiya.web.jwt.JwtProvider;
import com.yeogiya.web.jwt.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @Transactional
    public Long join(JoinRequestDto requestDto) {
        String rawPassword = requestDto.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        requestDto.setPassword(encPassword);
        return memberRepository.save(requestDto.toEntity()).getMemberId();
    }

    public String login(LoginRequestDto requestDto) {
       String id = requestDto.getId();
       String password = requestDto.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증이 완료된 객체이면,
        if(authentication.isAuthenticated()) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

            Long authenticatedMemberId = principalDetails.getMember().getMemberId();
            String authenticatedId = principalDetails.getMember().getId();
            String authenticatedName = principalDetails.getMember().getName();

            return "로그인 성공 " + jwtProvider.generateJwtToken(authenticatedMemberId, authenticatedId, authenticatedName);
        }

        return "로그인 실패";
    }
}
