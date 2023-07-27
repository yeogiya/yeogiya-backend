package com.yeogiya.repository;

import com.yeogiya.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(String Id);
    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByRefreshToken(String refreshToken);
}
