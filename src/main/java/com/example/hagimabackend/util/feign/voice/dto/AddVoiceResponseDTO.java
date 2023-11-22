package com.example.hagimabackend.util.feign.voice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddVoiceResponseDTO {
    @JsonProperty("voice_id")
    String voiceId;
}
