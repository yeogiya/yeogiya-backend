package com.yeogiya.web.member.dto.request;

import com.yeogiya.entity.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Getter
public class SignUpRequestDTO {

    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Pattern(regexp = "^[a-z0-9_-]{5,20}$", message = "아이디는 영어 소문자와 숫자 특수문자 _,-만 사용하여 5~20자리여야 합니다.")
    private String id;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$", message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    @Pattern(regexp = "^(?=.{1,12}$).*" , message = "닉네임은 1~12자리여야 합니다.")
    private String nickname;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email
    private String email;
    private String loginType;


    public Member toEntity() {
        return Member.builder()
                .id(id)
                .password(password)
                .nickname(nickname)
                .email(email)
                .build();
    }

}
