package com.example.hagimabackend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class HagimaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HagimaBackendApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:3000")
                        .allowedMethods(
                                HttpMethod.OPTIONS.name(),
                                HttpMethod.GET.name(),
                                HttpMethod.PATCH.name(),
                                HttpMethod.DELETE.name(),
                                HttpMethod.POST.name());
            }
        };
    }
}
