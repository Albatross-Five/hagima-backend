package com.example.hagimabackend.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GuestSignupResponseDTO {
    @Schema(description = "회원 UUID")
    String uuid;
    public GuestSignupResponseDTO(String uuid) {
        this.uuid = uuid;
    }
}
