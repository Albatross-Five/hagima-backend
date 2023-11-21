package com.example.hagimabackend.service;

import com.example.hagimabackend.util.feign.voice.VoiceFeignClient;
import com.example.hagimabackend.util.feign.dto.SpeechRequestDTO;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoiceService {
    @Value("${VOICE_API_KEY}")
    private String apiKey;

    private final VoiceFeignClient voiceFeignClient;

    public InputStream getSpeech(String text, String voiceId) {

        try (Response response = voiceFeignClient.createTextToSpeech(apiKey, voiceId, new SpeechRequestDTO(text))) {
            System.out.println(response.status());
            System.out.println(response.body().asInputStream().toString());
            System.out.println(convert(response.body().asInputStream()));
            return response.body().asInputStream();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String convert(InputStream inputStream) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return bufferedReader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
