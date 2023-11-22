package com.example.hagimabackend.util.feign.ml;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableFeignClients(basePackages = {"com.example.hagimabackend.util.feign.ml"})
public class MLFeignClientConfig {
    @Bean
    public Encoder feignFromEncoder() {
        return new SpringFormEncoder();
    }
}