package com.yeogiya.web.service;

import com.yeogiya.entity.member.LoginType;
import com.yeogiya.entity.member.Member;
import com.yeogiya.entity.member.Role;
import com.yeogiya.enumerable.EnumErrorCode;
import com.yeogiya.exception.ClientException;
import com.yeogiya.exception.ServerException;
import com.yeogiya.repository.MemberRepository;
import com.yeogiya.web.dto.MemberSignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(MemberSignUpDto memberSignUpDto) throws Exception {

        if (memberRepository.findById(memberSignUpDto.getEmail()).isPresent()) {
            throw new ClientException.Conflict(EnumErrorCode.ALREADY_EXISTS_ID);
        }
        if (memberRepository.findByEmail(memberSignUpDto.getEmail()).isPresent()) {
            throw new ClientException.Conflict(EnumErrorCode.ALREADY_EXISTS_EMAIL);
        }
        if (memberRepository.findByNickname(memberSignUpDto.getNickname()).isPresent()) {
            throw new ClientException.Conflict(EnumErrorCode.ALREADY_EXISTS_NICKNAME);
        }

        Member member = Member.builder()
                .id(memberSignUpDto.getId())
                .email(memberSignUpDto.getEmail())
                .loginType(LoginType.EMAIL)
                .password(memberSignUpDto.getPassword())
                .nickname(memberSignUpDto.getNickname())
                .role(Role.USER)
                .build();

        member.passwordEncode(passwordEncoder);
        memberRepository.save(member);

    }

    @Transactional(readOnly = true)
    public boolean checkIdDuplication(String id) {
        boolean idDupliacte = memberRepository.existsById(id);
        return idDupliacte;
    }

    @Transactional(readOnly = true)
    public boolean checkNicknameDuplication(String nickname) {
        boolean nicknameDuplicate = memberRepository.existsByNickname(nickname);
        return nicknameDuplicate;

    }

    @Transactional(readOnly = true)
    public boolean checkEmailDuplication(String email) {
        boolean emailDuplicate = memberRepository.existsByEmail(email);
        return emailDuplicate;
    }

}
