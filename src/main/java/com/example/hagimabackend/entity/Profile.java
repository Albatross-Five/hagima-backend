package com.example.hagimabackend.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tb_profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Member.class)
    @JoinColumn(name = "member_uuid", referencedColumnName = "uuid")
    private Member member;

    @NotNull
    @Column(length = 10)
    private String name;

    @NotNull
    private Integer pin;

    @Builder
    public Profile(Member member, @NotNull String name, @NotNull Integer pin) {
        this.member = member;
        this.name = name;
        this.pin = pin;
    }
}
