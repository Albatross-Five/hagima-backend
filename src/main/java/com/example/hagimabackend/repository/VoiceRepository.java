package com.example.hagimabackend.repository;

import com.example.hagimabackend.entity.Voice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoiceRepository extends JpaRepository<Voice, String> {
}
