package com.yeogiya.web.dto.member;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class ResetPasswordRequestDTO {

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$",
            message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
    @NotBlank(message = "패스워드는 필수 항목입니다.")
    private String password;

    @NotBlank(message = "토큰은 필수 항목입니다.")
    private String token;
}
