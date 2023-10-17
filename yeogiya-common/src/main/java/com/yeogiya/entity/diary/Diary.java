package com.yeogiya.entity.diary;

import com.yeogiya.entity.BaseTimeEntity;
import com.yeogiya.entity.member.LoginType;
import com.yeogiya.entity.member.Member;
import com.yeogiya.entity.member.Role;
import com.yeogiya.entity.member.Status;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary extends BaseTimeEntity {

    @Id
    @Column(name = "member_id", columnDefinition = "INT(11)")
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(name = "content", columnDefinition = "VARCHAR(2000)")
    private String content;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "open_yn", columnDefinition = "CHAR(1)")
    private OpenYn openYn;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "del_yn", columnDefinition = "CHAR(1)")
    private DelYn delYn;

    @Column(name = "deleted_at", columnDefinition = "DATETIME")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaryHashtag> diaryHashtags;


}
