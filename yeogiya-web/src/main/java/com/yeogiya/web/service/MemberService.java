package com.yeogiya.web.service;

import com.yeogiya.entity.member.LoginType;
import com.yeogiya.entity.member.Member;
import com.yeogiya.entity.member.Role;
import com.yeogiya.repository.MemberRepository;
import com.yeogiya.web.dto.MemberSignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(MemberSignUpDto memberSignUpDto) throws Exception {

        if (memberRepository.findById(memberSignUpDto.getId()).isPresent()) {
            throw new Exception("이미 존재하는 아이디입니다.");
        }

        if (memberRepository.findByNickname(memberSignUpDto.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        if (memberRepository.findByEmail(memberSignUpDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        Member member = Member.builder()
                .id(memberSignUpDto.getId())
                .email(memberSignUpDto.getEmail())
                .loginType(LoginType.EMAIL)
                .name(memberSignUpDto.getName())
                .mobileNo(memberSignUpDto.getMobileNo())
                .password(memberSignUpDto.getPassword())
                .nickname(memberSignUpDto.getNickname())
                .role(Role.USER)
                .build();

        member.passwordEncode(passwordEncoder);
        memberRepository.save(member);
    }
}
