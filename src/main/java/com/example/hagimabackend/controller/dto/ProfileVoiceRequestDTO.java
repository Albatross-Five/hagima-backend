package com.example.hagimabackend.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class ProfileVoiceRequestDTO {
    @Schema(description = "음성 생성 요청한 프로필 닉네임")
    private String nickname;

    @Schema(description = "음성 파일 배열")
    private MultipartFile[] files;

    @Schema(description = "true = 반말, false = 존댓말")
    private Boolean informal;

}
