package com.example.hagimabackend.util.feign.voice;

import feign.FeignException;
import feign.Logger;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;


@Configuration
@EnableFeignClients(basePackages = {"com.example.hagimabackend.util.feign.voice"})
public class VoiceFeignClientConfig {
    @Bean
    public Encoder feignEncoder() {
        return new VoiceEncoder();
    }

    @Bean
    public Decoder feignDecoder() {
        return new VoiceDecoder();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    static class VoiceDecoder implements Decoder {

        @Override
        public Object decode(Response response, Type type) throws IOException, FeignException {
            if (type instanceof Class && InputStream.class.isAssignableFrom((Class<?>) type)) {
                return response.body().asInputStream();
            }
            return new GsonDecoder().decode(response, type);
        }
    }

    static class VoiceEncoder implements Encoder {

        private final Encoder jsonEncoder = new GsonEncoder();
        private final Encoder formEncoder = new SpringFormEncoder();

        @Override
        public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
            if (isMultipartRequest(template)) {
                formEncoder.encode(object, bodyType, template);
            } else {
                jsonEncoder.encode(object, bodyType, template);
            }
        }

        private boolean isMultipartRequest(RequestTemplate template) {
            Collection<String> contentTypes = template.headers().getOrDefault("Content-Type", Collections.emptyList());
            return contentTypes.stream().anyMatch(type -> type.contains("multipart/form-data"));
        }
    }

}

