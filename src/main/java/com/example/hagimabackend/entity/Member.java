package com.example.hagimabackend.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Table(name = "tb_member")
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    private String kakao_id;

    @Builder
    public Member(@NotNull String uuid) {
        this.kakao_id = uuid;
    }
}
