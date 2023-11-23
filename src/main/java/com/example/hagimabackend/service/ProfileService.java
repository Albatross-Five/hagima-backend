package com.example.hagimabackend.service;


import com.example.hagimabackend.controller.dto.ProfileRequestDTO;
import com.example.hagimabackend.controller.dto.ProfileResponseDTO;
import com.example.hagimabackend.entity.Member;
import com.example.hagimabackend.entity.Profile;
import com.example.hagimabackend.repository.ProfileRepository;
import com.example.hagimabackend.util.exception.BusinessException;
import com.example.hagimabackend.util.exception.ErrorCode;
import com.example.hagimabackend.util.feign.ml.MLFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final String PROFILE_BUCKET = "hagima-face";
    private final ProfileRepository profileRepository;
    private final StorageService storageService;
    private final MLFeignClient mlFeignClient;


    public void register(Member member, ProfileRequestDTO profileRequest) {
        Profile profile = Profile.builder()
                .member(member)
                .name(profileRequest.getNickname())
                .pin(profileRequest.getPin()).build();

        String name = member.getUuid().toString() + "=" + profile.getName();
        storageService.uploadMultipartFile(PROFILE_BUCKET, name, profileRequest.getFaceImg());
        profileRepository.save(profile);
    }

    public List<ProfileResponseDTO> getProfiles(String uuid) {
        storageService.setCORSConfig();
        List<Profile> profiles = profileRepository.findAllByUUID(UUID.fromString(uuid));
        List<ProfileResponseDTO> response = new ArrayList<>();
        profiles.forEach(profile -> response.add(new ProfileResponseDTO(profile.getName(), storageService.getObjectUrl(PROFILE_BUCKET, uuid + "=" + profile.getName()))));
        return response;
    }

    public Profile getProfile(UUID uuid, String nickname) {
        return profileRepository.findByUUIDAndNickname(uuid, nickname).orElseThrow(() -> new BusinessException(ErrorCode.Profile_NOT_FOUND));
    }

    public Object recognition(UUID uuid, MultipartFile current) {
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

        int size = formData.size();
        Object result = mlFeignClient.recognition(formData.get(0), size >= 2 ? formData.get(1) : null, size >= 3 ? formData.get(2) : null, size >= 4 ? formData.get(3) : null, current);
        System.out.println(result);

        return result;
    }
}
