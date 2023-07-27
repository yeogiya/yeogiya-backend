package com.yeogiya.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberSignUpDto {
    private String id;
    private String password;
    private String nickname;
    private String email;
    private String loginType;
    private String name;
    private String mobileNo;
}
