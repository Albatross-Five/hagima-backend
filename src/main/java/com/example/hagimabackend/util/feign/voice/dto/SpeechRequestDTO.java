package com.example.hagimabackend.util.feign.voice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpeechRequestDTO {
    private String model_id = null;
    private String text = null;
    private VoiceSettings voice_settings = null;

    public SpeechRequestDTO(String text) {
        this.model_id = "eleven_multilingual_v2";
        this.voice_settings = new VoiceSettings();
        this.text = text;
    }

    @Getter
    @Setter
    static
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
}
