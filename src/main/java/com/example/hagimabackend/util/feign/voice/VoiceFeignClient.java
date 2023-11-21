package com.example.hagimabackend.util.feign.voice;

import com.example.hagimabackend.util.feign.dto.SpeechRequestDTO;
import feign.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@FeignClient(name = "voiceFeignClient", url = "https://api.elevenlabs.io/v1", configuration = VoiceFeignClientConfig.class)
public interface VoiceFeignClient {
    @PostMapping("/text-to-speech/{id}?output_format=mp3_44100_64")
    @Headers({
            "Content-Type: application/json",
            "Accept: audio/mpeg"
    })
    Response createTextToSpeech(@RequestHeader("xi-api-key") String apiKey, @PathVariable("id") String id, SpeechRequestDTO request);
}