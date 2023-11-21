package com.example.hagimabackend.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ProfileResponseDTO {
    @Schema(description = "프로필 닉네임")
    String nickname;

    @Schema(description = "프로필 이미지 url")
    String profileUrl;

    public ProfileResponseDTO(String nickname, String profileUrl) {
        this.nickname = nickname;
        this.profileUrl = profileUrl;
    }
}