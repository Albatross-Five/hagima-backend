package com.example.hagimabackend.repository;

import com.example.hagimabackend.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("select p from Profile p where p.member.uuid = :uuid")

    List<Profile> findAllByUUID(UUID uuid);
}
