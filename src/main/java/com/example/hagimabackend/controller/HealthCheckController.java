package com.example.hagimabackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@Tag(name="Health", description = "LB Health Check API")
public class HealthCheckController {

    @GetMapping("")
    @Operation(summary = "로드밸런서 서버 상태 확인용 API")
    public String healthCheck() {
        return "Server is healthy.";
    }
}
