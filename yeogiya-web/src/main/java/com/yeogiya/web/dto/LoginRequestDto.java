package com.yeogiya.web.dto;

import com.yeogiya.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {
    private String id;
    private String password;

    @Builder
    public LoginRequestDto(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .password(password)
                .build();
    }
}
