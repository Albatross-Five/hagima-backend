package com.example.hagimabackend.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ProfileRecognitionResponseDTO {
    @Schema(description = "프로필 닉네임")
    private final String nickname;

    public ProfileRecognitionResponseDTO(String nickname) {
        this.nickname = nickname;
    }
}
