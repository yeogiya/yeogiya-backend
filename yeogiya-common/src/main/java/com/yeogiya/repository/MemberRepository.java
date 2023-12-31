package com.yeogiya.repository;

import com.yeogiya.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(String id);
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByRefreshToken(String refreshToken);

    boolean existsById(String id);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);

    Optional<Member> findByIdAndEmail(@NotNull String id, @Email @NotNull String email);
}
