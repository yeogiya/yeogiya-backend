package com.yeogiya.web.dto.member;

import com.yeogiya.entity.member.LoginType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    private String id;
    private String password;

    @NotNull
    private LoginType loginType;
}
