package com.example.hagimabackend.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tb_voice")
public class Voice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Profile.class)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    @NotNull
    @Column(length = 30)
    private String name;

    @Builder
    public Voice(Profile profile, @NotNull String name) {
        this.profile = profile;
        this.name = name;
    }
}
