package com.example.hagimabackend.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class ProfileVoiceRequestDTO {
    @Schema(description = "지인 목소리 이름")
    private String name;

    @Schema(description = "음성 파일 배열")
    private MultipartFile[] files;

}
