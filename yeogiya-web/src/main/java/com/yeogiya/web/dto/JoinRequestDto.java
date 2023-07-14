package com.yeogiya.web.dto;

import com.yeogiya.entity.member.LoginType;
import com.yeogiya.entity.member.Member;
import com.yeogiya.entity.member.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor
public class JoinRequestDto {
    private String id;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String mobileNo;
    private String profileImg;
    private LoginType loginType;
    private String introduce;

    @Builder
    public JoinRequestDto(String id, String email, String password, String name, String nickname, String mobileNo, String profileImg, LoginType loginType, String introduce) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.mobileNo = mobileNo;
        this.profileImg = profileImg;
        this.loginType = loginType;
        this.introduce = introduce;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .mobileNo(mobileNo)
                .profileImg(profileImg)
                .loginType(loginType)
                .introduce(introduce)
                .build();

    }

}
