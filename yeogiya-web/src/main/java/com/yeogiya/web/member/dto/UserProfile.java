package com.yeogiya.web.member.dto;

import com.yeogiya.entity.member.LoginType;
import com.yeogiya.entity.member.Member;
import com.yeogiya.entity.member.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserProfile {

    private String id;
    private String email;
    private String name;
    private String imageUrl;

    public Member toMember(LoginType loginType, String nickname) {
        return Member.builder()
                .id(id)
                .email(email)
                .profileImg(imageUrl)
                .nickname(nickname)
                .loginType(loginType)
                .role(Role.USER)
                .build();
    }
}
