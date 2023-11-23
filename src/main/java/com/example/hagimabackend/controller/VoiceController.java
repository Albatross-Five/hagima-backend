package com.example.hagimabackend.controller;

import com.example.hagimabackend.controller.dto.ProfileVoiceListResponseDTO;
import com.example.hagimabackend.controller.dto.ProfileVoiceRequestDTO;
import com.example.hagimabackend.entity.Voice;
import com.example.hagimabackend.global.response.DataResponse;
import com.example.hagimabackend.global.response.MessageResponse;
import com.example.hagimabackend.service.MemberService;
import com.example.hagimabackend.service.StorageService;
import com.example.hagimabackend.service.VoiceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class VoiceController {
    private final VoiceService voiceService;
    private final MemberService memberService;
    private final StorageService storageService;

    @PostMapping(value = "/voice", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "지인 음성 등록")
    public ResponseEntity<MessageResponse> registerVoice(@ModelAttribute ProfileVoiceRequestDTO profileVoice) {
        UUID uuid = memberService.getMemberByHeader().getUuid();
        Voice voice = voiceService.addVoice(uuid, profileVoice.getNickname(), profileVoice.getFiles());
        voiceService.createWarningVoices(uuid.toString(), profileVoice.getNickname(), voice, profileVoice.getInformal());
        return new ResponseEntity<>(MessageResponse.of(HttpStatus.OK, "AI Voice 생성 성공."), HttpStatus.OK);
    }

    @GetMapping(value = "/voice/{nickname}")
    @Operation(summary = "음성 링크 가져오기")
    public ResponseEntity<DataResponse<List<ProfileVoiceListResponseDTO>>> getVoices(@PathVariable("nickname") String nickname) {
        return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "음성 목록 조회 성공.",
                voiceService.getVoices(memberService.getMemberByHeader().getUuid(), nickname)), HttpStatus.OK);
    }

}
