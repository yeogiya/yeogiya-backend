package com.yeogiya.entity.member;

import com.yeogiya.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @Column(name = "member_id", columnDefinition = "INT(11)")
    @GeneratedValue(strategy = IDENTITY)
    private long memberId;

    @NotNull
    @Column(name = "id", columnDefinition = "VARCHAR(50)")
    private String id;

    @Email
    @NotNull
    @Column(name = "email", columnDefinition = "VARCHAR(255)")
    private String email;

    @Column(name = "password", columnDefinition = "VARCHAR(255)")
    private String password;

    @Column(name = "name", columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(name = "nickname", columnDefinition = "VARCHAR(50)")
    private String nickname;

    @Column(name = "mobile_no", columnDefinition = "VARCHAR(11)")
    private String mobileNo;

    @Column(name = "profile_img", columnDefinition = "VARCHAR(255)")
    private String profileImg;

    @NotNull
    @Column(name = "login_type", columnDefinition = "VARCHAR(10)")
    private LoginType loginType;

    @Column(name = "introduce", columnDefinition = "VARCHAR(255)")
    private String introduce;

    @Column(name = "warning_count", columnDefinition = "INT(11)")
    private int warningCount;

    @Column(name = "status", columnDefinition = "CHAR(1)")
    private Status status;

    @Column(name = "withdrawal_at", columnDefinition = "DATETIME")
    private LocalDateTime withdrawalAt;

    @Column(name = "is_open_area", columnDefinition = "CHAR(1)")
    private boolean isOpenArea;

    @Column(name = "is_open_bookmark", columnDefinition = "CHAR(1)")
    private boolean isOpenBookmark;
}
