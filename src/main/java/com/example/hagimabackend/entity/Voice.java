package com.example.hagimabackend.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_voice")
public class Voice {
    @Id
    private String id;

    @ManyToOne(targetEntity = Profile.class)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    private Boolean status;

    @Builder
    public Voice(Profile profile, @NotNull String voiceId, Boolean status) {
        this.profile = profile;
        this.id = voiceId;
        this.status = status;
    }
}
