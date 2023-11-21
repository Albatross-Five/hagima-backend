package com.example.hagimabackend.util.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SpeechRequestDTO {
    private final String model_id;
    private final String text;
    private final VoiceSettings voice_settings;

    public SpeechRequestDTO(String text) {
        this.model_id = "eleven_multilingual_v2";
        this.voice_settings = new VoiceSettings();
        this.text = text;
    }
}

@Getter
class VoiceSettings {
    private final int similarity_boost;
    private final int stability;
    private final int style;
    private final boolean use_speaker_boost;

    VoiceSettings() {
        this.similarity_boost = 0;
        this.stability = 0;
        this.style = 0;
        this.use_speaker_boost = true;
    }
}
