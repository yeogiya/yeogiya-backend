package com.yeogiya.web.member.dto.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class FindIdRequestDTO {

    @Email
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;
}
