package com.example.hagimabackend.controller;

import com.example.hagimabackend.controller.dto.ProfileRequestDTO;
import com.example.hagimabackend.controller.dto.ProfileResponseDTO;
import com.example.hagimabackend.entity.CustomUserDetails;
import com.example.hagimabackend.entity.Member;
import com.example.hagimabackend.global.response.DataResponse;
import com.example.hagimabackend.global.response.MessageResponse;
import com.example.hagimabackend.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@Tag(name="프로필", description = "프로필 관련 API")
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "프로필 등록")
    public ResponseEntity<MessageResponse> registerProfile (@ModelAttribute ProfileRequestDTO profile) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        profileService.register(userDetails.getMember(), profile);

        return new ResponseEntity<>(MessageResponse.of(HttpStatus.OK, "프로필 등록 성공"), HttpStatus.OK);
    }

    @GetMapping("")
    @Operation(summary = "프로필 목록 조회")
    public ResponseEntity<DataResponse<List<ProfileResponseDTO>>> getProfiles () {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member =  userDetails.getMember();
        return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "프로필 조회 성공", profileService.getProfiles(member.getUuid().toString())), HttpStatus.OK);
    }
}
