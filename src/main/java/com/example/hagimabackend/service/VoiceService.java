package com.example.hagimabackend.service;

import com.example.hagimabackend.entity.DefineWarning;
import com.example.hagimabackend.entity.Profile;
import com.example.hagimabackend.entity.Voice;
import com.example.hagimabackend.repository.VoiceRepository;
import com.example.hagimabackend.repository.WarningRepository;
import com.example.hagimabackend.util.exception.BusinessException;
import com.example.hagimabackend.util.exception.ErrorCode;
import com.example.hagimabackend.util.feign.voice.VoiceFeignClient;
import com.example.hagimabackend.util.feign.voice.dto.AddVoiceResponseDTO;
import com.example.hagimabackend.util.feign.voice.dto.SpeechRequestDTO;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoiceService {
    @Value("${VOICE_API_KEY}")
    private String apiKey;
    private final String VOICE_BUCKET = "hagima-voice";

    private final VoiceFeignClient voiceFeignClient;

    private final ProfileService profileService;
    private final StorageService storageService;

    private final VoiceRepository voiceRepository;
    private final WarningRepository warningRepository;

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

    public Voice addVoice(UUID uuid, String nickname, MultipartFile[] files) {
        Profile profile = profileService.getProfile(uuid, nickname);

        AddVoiceResponseDTO response = voiceFeignClient.addVoice(apiKey, files, uuid + "=" + nickname);
        System.out.println(response.getVoiceId());
        Voice voice = Voice.builder()
                .profile(profile)
                .voiceId(response.getVoiceId())
                .status(false)
                .build();
        voiceRepository.save(voice);

        return voice;
    }

    @Async
    public void createWarningVoices(String uuid, String nickname, Voice voice, Boolean informal) {
        List<DefineWarning> warningList = warningRepository.getWarningByInformal(informal);
        try {
            for (DefineWarning warning : warningList) {
                String text = warning.getText();
                String type = warning.getType();

                Response speech = getSpeech(text, voice.getId());
                storageService.uploadInputStream(VOICE_BUCKET, uuid + "=" + nickname + "=" + type + ".mp3", "audio/mpeg", Long.parseLong(speech.headers().get("Content-length").iterator().next()), speech.body().asInputStream());
            }
            
            voice.setStatus(true);
            voiceRepository.save(voice);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }
}
