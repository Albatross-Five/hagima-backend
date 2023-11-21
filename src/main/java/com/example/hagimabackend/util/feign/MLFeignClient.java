package com.example.hagimabackend.util.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


@Component
@FeignClient(name = "mlClient", url = "https://hgm-ml.p-e.kr", configuration = FeignClientConfig.class)
public interface MLFeignClient {

    @PostMapping(consumes = "multipart/form-data", value = "/profile/recognition")
    String recognition(
            @RequestPart("profile1") MultipartFile profile1,
            @RequestPart("profile2") MultipartFile profile2,
            @RequestPart("profile3") MultipartFile profile3,
            @RequestPart("profile4") MultipartFile profile4,
            @RequestPart("current") MultipartFile current
    );
}