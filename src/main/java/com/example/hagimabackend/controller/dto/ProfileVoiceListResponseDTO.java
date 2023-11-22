package com.example.hagimabackend.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ProfileVoiceListResponseDTO {
    @Schema(description = "음성 타입 (졸음 / 휴대폰)")
    private final String type;

    @Schema(description = "다운로드 링크")
    private final String url;

    public ProfileVoiceListResponseDTO(String type, String url) {
        this.type = type;
        this.url = url;
    }
}
