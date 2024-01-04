package com.yeogiya.repository;

import com.yeogiya.entity.member.PasswordResetEmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PasswordResetEmailTokenRepository extends JpaRepository<PasswordResetEmailToken, Integer> {

    Optional<PasswordResetEmailToken> findByIdAndExpired(String id, boolean expired);
}
