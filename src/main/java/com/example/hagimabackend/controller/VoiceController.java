package com.example.hagimabackend.controller;

import com.example.hagimabackend.controller.dto.ProfileVoiceListResponseDTO;
import com.example.hagimabackend.controller.dto.ProfileVoiceRequestDTO;
import com.example.hagimabackend.entity.Voice;
import com.example.hagimabackend.global.response.DataResponse;
import com.example.hagimabackend.global.response.MessageResponse;
import com.example.hagimabackend.service.MemberService;
import com.example.hagimabackend.service.ProfileService;
import com.example.hagimabackend.service.StorageService;
import com.example.hagimabackend.service.VoiceService;
import feign.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class VoiceController {
    private final VoiceService voiceService;
    private final MemberService memberService;
    private final ProfileService profileService;
    private final StorageService storageService;

    @PostMapping(value = "/voice", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "지인 음성 등록")
    public ResponseEntity<MessageResponse> registerVoice(@ModelAttribute ProfileVoiceRequestDTO profileVoice) {
        UUID uuid = memberService.getMemberByHeader().getUuid();
        Voice voice = voiceService.addVoice(uuid, profileVoice.getNickname(), profileVoice.getFiles());
        voiceService.createWarningVoices(uuid.toString(), profileVoice.getNickname(), voice, profileVoice.getInformal());
        return new ResponseEntity<>(MessageResponse.of(HttpStatus.OK, "AI Voice 생성 성공."), HttpStatus.OK);
    }

    @GetMapping(value = "/sample")
    @Operation(summary = "샘플 음성 링크 가져오기")
    public ResponseEntity<byte[]> getSampleVoice() {
        Response audio = voiceService.getSpeech("승훈아 짜장면 먹어라", "uvGyrpqm6nghsXOIKb1u");
        // InputStream에서 데이터를 읽어옵니다.
        try (InputStream inputStream = audio.body().asInputStream()) {
            byte[] audioData = inputStream.readAllBytes();

            // HTTP 응답 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentLength(audioData.length);
            return new ResponseEntity<>(audioData, headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        return new ResponseEntity<>(storageService.getObjectUrl("hagima-voice", "test.mp3"), HttpStatus.OK);
    }

    @GetMapping(value = "/voice/{nickname}")
    @Operation(summary = "음성 링크 가져오기")
    public ResponseEntity<DataResponse<List<ProfileVoiceListResponseDTO>>> getVoices(@PathVariable("nickname") String nickname) {
        UUID uuid = memberService.getMemberByHeader().getUuid();

        String phone = storageService.getObjectUrl("hagima-voice", uuid + "=" + nickname + "=phone.mp3");
        String sleep = storageService.getObjectUrl("hagima-voice", uuid + "=" + nickname + "=sleep.mp3");

        List<ProfileVoiceListResponseDTO> list = new ArrayList<>();
        list.add(new ProfileVoiceListResponseDTO("phone", phone));
        list.add(new ProfileVoiceListResponseDTO("sleep", sleep));

        return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "음성 목록 조회 성공.", list), HttpStatus.OK);
    }

}
