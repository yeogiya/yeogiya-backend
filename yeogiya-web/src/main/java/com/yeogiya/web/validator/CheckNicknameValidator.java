package com.yeogiya.web.validator;

import com.yeogiya.repository.MemberRepository;
import com.yeogiya.web.dto.MemberSignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckNicknameValidator extends AbstractValidator<MemberSignUpDto> {
    private final MemberRepository memberRepository;

    @Override
    protected void doValidate(MemberSignUpDto memberSignUpDto, Errors errors) {
        if (memberRepository.existsByNickname(memberSignUpDto.toEntity().getNickname())) {
            errors.rejectValue("nickname","닉네임 중복 오류", "이미 사용 중인 닉네임입니다.");
        }
    }
}
