package com.yeogiya.web.validator;

import com.yeogiya.repository.MemberRepository;
import com.yeogiya.web.dto.MemberSignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckIdValidator extends AbstractValidator<MemberSignUpDto> {
    private final MemberRepository memberRepository;

    @Override
    protected void doValidate(MemberSignUpDto memberSignUpDto, Errors errors) {
        if (memberRepository.existsById(memberSignUpDto.toEntity().getId())) {
            errors.rejectValue("id","아이디 중복 오류", "이미 사용 중인 아이디입니다.");
        }
    }
}
