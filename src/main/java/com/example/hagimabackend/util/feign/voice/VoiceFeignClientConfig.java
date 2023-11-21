package com.example.hagimabackend.util.feign.voice;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.StringDecoder;
import feign.form.spring.SpringFormEncoder;
import feign.jackson.JacksonEncoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonEncoder;


@Configuration
@EnableFeignClients(basePackages = {"com.example.hagimabackend.util.feign.voice"})
public class VoiceFeignClientConfig {
    @Bean
    public Encoder feignEncoder() {
        return new JacksonEncoder();
    }

    @Bean
    public Decoder feignDecoder() {
        return new StringDecoder();
    }
}
