package com.example.hagimabackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tb_define_warning")
public class DefineWarning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String type;

    @NotNull
    private Boolean informal;

    @NotNull
    private String text;
}
