package com.example.hagimabackend.controller;

import com.example.hagimabackend.controller.dto.GuestSignupResponseDTO;
import com.example.hagimabackend.entity.Member;
import com.example.hagimabackend.global.response.DataResponse;
import com.example.hagimabackend.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupController {
    private final MemberService memberService;

    @GetMapping("")
    @Operation(summary = "게스트 회원 가입")
    public ResponseEntity<DataResponse<GuestSignupResponseDTO>> guestSignup() {
        Member member = memberService.register();

        return new ResponseEntity<>(
                DataResponse.of(HttpStatus.OK, "회원 가입 성공", new GuestSignupResponseDTO(member.getKakao_id())), HttpStatus.OK);

    }
}
