package com.example.hagimabackend.repository;

import com.example.hagimabackend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.uuid = :uuid")
    Optional<Member> findByUUID(@Param("uuid") UUID uuid);
}
