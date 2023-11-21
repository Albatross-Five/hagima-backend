package com.example.hagimabackend.controller;

import com.example.hagimabackend.controller.dto.ProfileVoiceRequestDTO;
import com.example.hagimabackend.service.VoiceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class VoiceController {
    private final VoiceService voiceService;

    @PostMapping(value = "/voice", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "지인 음성 등록")
    public String registerVoice(@ModelAttribute ProfileVoiceRequestDTO profileVoice) {

        return profileVoice.getName();
    }

    @GetMapping(value = "/sample", produces = "audio/mpeg")
    @Operation(summary = "샘플 음성 듣기")
    public ResponseEntity<InputStreamResource> getSampleVoice() {
        InputStream audioStream = voiceService.getSpeech("승훈아 짜장면 먹어라", "uvGyrpqm6nghsXOIKb1u");
        System.out.println(audioStream.toString());
        return new ResponseEntity<>(new InputStreamResource(audioStream), HttpStatus.OK);
    }
}
