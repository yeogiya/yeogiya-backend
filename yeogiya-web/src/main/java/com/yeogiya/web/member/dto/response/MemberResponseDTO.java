package com.yeogiya.web.member.dto.response;

import com.yeogiya.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDTO {

    private String nickname;
    private String id;
    private String email;
    private String profileImageUrl;

    public MemberResponseDTO(Member member) {
        this.nickname = member.getNickname();
        this.id = member.getId();
        this.email = member.getEmail();
        this.profileImageUrl = member.getProfileImg();
    }
}
