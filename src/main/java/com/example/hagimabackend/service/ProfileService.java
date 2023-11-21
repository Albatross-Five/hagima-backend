package com.example.hagimabackend.service;


import com.example.hagimabackend.controller.dto.ProfileRequestDTO;
import com.example.hagimabackend.controller.dto.ProfileResponseDTO;
import com.example.hagimabackend.entity.Member;
import com.example.hagimabackend.entity.Profile;
import com.example.hagimabackend.repository.ProfileRepository;
import com.example.hagimabackend.util.feign.ml.MLFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final StorageService storageService;
    private final MLFeignClient mlFeignClient;


    public void register(Member member, ProfileRequestDTO profileRequest) {
        Profile profile = Profile.builder()
                .member(member)
                .name(profileRequest.getNickname())
                .pin(profileRequest.getPin()).build();

        String name = member.getUuid().toString() + "=" + profile.getName();
        storageService.uploadProfile(name, profileRequest.getFaceImg());
        profileRepository.save(profile);
    }

    public List<ProfileResponseDTO> getProfiles(String uuid) {
        List<Profile> profiles = profileRepository.findAllByUUID(UUID.fromString(uuid));
        List<ProfileResponseDTO> response = new ArrayList<>();
        profiles.forEach(profile -> response.add(new ProfileResponseDTO(profile.getName(), storageService.getProfileUrl(uuid + "=" + profile.getName()))));
        return response;
    }

    public String recognition(UUID uuid, MultipartFile current) {
        List<String> fileNames = profileRepository.findAllByUUID(uuid).stream().map(profile -> uuid.toString() + "=" + profile.getName()).toList();

        AtomicInteger count = new AtomicInteger(0);
        ArrayList<MultipartFile> files = storageService.getProfileImages(fileNames);
        ArrayList<MultipartFile> formData = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                formData.add(new MockMultipartFile("profile" + count.addAndGet(1), file.getOriginalFilename(), file.getContentType(), file.getInputStream()));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        formData.forEach(file -> System.out.println(file.getName() + " " + file.getContentType() + " " + file.getSize()));

        int size = formData.size();
        String result = mlFeignClient.recognition(formData.get(0), size >= 2 ? formData.get(1) : null, size >= 3 ? formData.get(2) : null, size >= 4 ? formData.get(3) : null, current);
        System.out.println(result);

        return result;
    }
}
