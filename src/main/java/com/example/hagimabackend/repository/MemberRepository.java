package com.example.hagimabackend.repository;

import com.example.hagimabackend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.kakao_id = :uuid")
    Optional<Member> findByUUID(@Param("uuid") String uuid);
}
