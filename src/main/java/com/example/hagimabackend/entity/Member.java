package com.example.hagimabackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

@Entity
@Getter
@Table(name = "tb_member")
public class Member {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid2", type = org.hibernate.id.uuid.UuidGenerator.class)
    @Column(length = 16)
    private UUID member_uuid;

    @OneToOne(targetEntity = Kakao.class)
    @Value("id")
    private Long id;

    @NotNull
    @Column(length = 10)
    private String name;

    @NotNull
    private Integer pin;
}
