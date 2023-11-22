package com.example.hagimabackend.util.feign.voice;

import com.example.hagimabackend.util.feign.voice.dto.AddVoiceResponseDTO;
import com.example.hagimabackend.util.feign.voice.dto.SpeechRequestDTO;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Component
@FeignClient(name = "voiceFeignClient", url = "https://api.elevenlabs.io/v1", configuration = VoiceFeignClientConfig.class)
public interface VoiceFeignClient {
    @PostMapping(value = "/text-to-speech/{id}?output_format=mp3_44100_64", headers = "Content-Type=application/json")
    Response createTextToSpeech(@RequestHeader("xi-api-key") String apiKey, @PathVariable("id") String id, @RequestBody SpeechRequestDTO request);

    @PostMapping(value = "/voices/add", headers = "Content-type=multipart/form-data")
    AddVoiceResponseDTO addVoice(@RequestHeader("xi-api-key") String apiKey, @RequestPart MultipartFile[] files, @RequestPart String name);
}