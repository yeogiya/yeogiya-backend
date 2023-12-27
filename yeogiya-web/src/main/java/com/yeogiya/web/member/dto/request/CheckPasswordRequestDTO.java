package com.yeogiya.web.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckPasswordRequestDTO {

    @NotBlank(message = "패스워드는 필수 항목입니다.")
    private String password;
}
