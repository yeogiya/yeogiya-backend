package com.yeogiya.entity.member;

import com.yeogiya.entity.BaseTimeEntity;
import com.yeogiya.entity.diary.Diary;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
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

    @Column(name = "nickname", columnDefinition = "VARCHAR(50)")
    private String nickname;

    @Column(name = "profile_img", columnDefinition = "VARCHAR(255)")
    private String profileImg;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "login_type", columnDefinition = "VARCHAR(10)")
    private LoginType loginType;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "VARCHAR(10)")
    private Role role;

    @Column(name = "introduce", columnDefinition = "VARCHAR(255)")
    private String introduce;

    @Column(name = "warning_count", columnDefinition = "INT(11)")
    private int warningCount;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", columnDefinition = "CHAR(1)")
    private Status status;

    @Column(name = "withdrawal_at", columnDefinition = "DATETIME")
    private LocalDateTime withdrawalAt;

    @Column(name = "is_open_area", columnDefinition = "CHAR(1)")
    private boolean isOpenArea;

    @Column(name = "is_open_bookmark", columnDefinition = "CHAR(1)")
    private boolean isOpenBookmark;

    @Column(name = "refresh_token", columnDefinition = "VARCHAR(255)")
    private String refreshToken;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Diary> diarys = new ArrayList<>();

    // 비밀번호 암호화 메소드
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    public void resetPassword(PasswordEncoder passwordEncoder, String password) {
        this.password = passwordEncoder.encode(password);
    }
}
