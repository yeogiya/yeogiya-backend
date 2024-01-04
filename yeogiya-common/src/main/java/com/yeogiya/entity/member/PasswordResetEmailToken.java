package com.yeogiya.entity.member;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.yeogiya.entity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "password_reset_email_token")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PasswordResetEmailToken extends BaseTimeEntity {

    @Id
    private String id;

    @Column(name = "member_id")
    private Long memberId;

    @Builder.Default
    private boolean expired = false;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "expiration_datetime")
    private LocalDateTime expirationDateTime;

    public void changeToExpired() {
        this.expired = true;
    }

    public boolean isExpired() {
        return this.expirationDateTime.isBefore(LocalDateTime.now());
    }
}
