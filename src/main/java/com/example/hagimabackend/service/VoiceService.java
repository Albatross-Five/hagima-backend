package com.example.hagimabackend.service;

import com.example.hagimabackend.entity.Profile;
import com.example.hagimabackend.entity.Voice;
import com.example.hagimabackend.repository.VoiceRepository;
import com.example.hagimabackend.util.feign.voice.VoiceFeignClient;
import com.example.hagimabackend.util.feign.voice.dto.AddVoiceResponseDTO;
import com.example.hagimabackend.util.feign.voice.dto.SpeechRequestDTO;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoiceService {
    @Value("${VOICE_API_KEY}")
    private String apiKey;

    private final VoiceFeignClient voiceFeignClient;
    private final ProfileService profileService;
    private final VoiceRepository voiceRepository;

    public Response getSpeech(String text, String voiceId) {
        return voiceFeignClient.createTextToSpeech(apiKey, voiceId, new SpeechRequestDTO(text));
    }

    public String convert(InputStream inputStream) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return bufferedReader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addVoice(UUID uuid, String nickname, MultipartFile[] files) {
        Profile profile = profileService.getProfile(uuid, nickname);

        AddVoiceResponseDTO response = voiceFeignClient.addVoice(apiKey, files, uuid + "=" + nickname);
        Voice voice = Voice.builder()
                .profile(profile)
                .voiceId(response.getVoiceId())
                .build();
        voiceRepository.save(voice);
    }
}
