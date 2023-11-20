package com.example.hagimabackend.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class ProfileRequestDTO {
    @Schema(description = "프로필 닉네임")
    private String nickname;

    @Schema(description = "얼굴 이미지 파일")
    private MultipartFile faceImg;

    @Schema(description = "핀 번호")
    private Integer pin;
}
