package com.example.hagimabackend.service;


import com.example.hagimabackend.controller.dto.ProfileRequestDTO;
import com.example.hagimabackend.controller.dto.ProfileResponseDto;
import com.example.hagimabackend.entity.Member;
import com.example.hagimabackend.entity.Profile;
import com.example.hagimabackend.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final StorageService storageService;

    public void register(Member member, ProfileRequestDTO profileRequest) {
        Profile profile = Profile.builder()
                .member(member)
                .name(profileRequest.getNickname())
                .pin(profileRequest.getPin()).build();

        System.out.println(profileRequest.getFaceImg().getSize());
        String name = member.getUuid().toString() + "=" + profile.getName();
        storageService.uploadProfile(name, profileRequest.getFaceImg());
        profileRepository.save(profile);
    }

    public List<ProfileResponseDto> getProfiles(String uuid) {
        List<Profile> profiles = profileRepository.findAllByUUID(UUID.fromString(uuid));
        List<ProfileResponseDto> response = new ArrayList<>();
        profiles.forEach(profile -> {
            response.add(new ProfileResponseDto(profile.getName(), storageService.getProfileUrl(uuid + "=" + profile.getName())));
        });
        return response;
    }
}