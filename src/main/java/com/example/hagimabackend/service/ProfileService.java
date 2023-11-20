package com.example.hagimabackend.service;


import com.example.hagimabackend.controller.dto.ProfileRequestDTO;
import com.example.hagimabackend.entity.Member;
import com.example.hagimabackend.entity.Profile;
import com.example.hagimabackend.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final StorageService storageService;

    public Profile register(Member member, ProfileRequestDTO profileRequest) throws IOException {
        Profile profile = Profile.builder()
                .member(member)
                .name(profileRequest.getNickname())
                .pin(profileRequest.getPin()).build();

        System.out.println(profileRequest.getFaceImg().getSize());
        String name = member.getUuid().toString() + "=" + profile.getName();
        storageService.uploadProfile(name, profileRequest.getFaceImg());
        profileRepository.save(profile);
        return profile;
    }
}
