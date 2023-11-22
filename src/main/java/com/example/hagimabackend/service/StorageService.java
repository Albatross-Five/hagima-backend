package com.example.hagimabackend.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class StorageService {
    final String endPoint = "https://kr.object.ncloudstorage.com";
    final String regionName = "kr-standard";
    private final String FACE_BUCKET = "hagima-face";

    private final String VOICE_BUCKET = "hagima-voice";
    private final AmazonS3 s3;

    public StorageService(Environment environment) {
        this.s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(Objects.requireNonNull(environment.getProperty("API_ACCESS")), Objects.requireNonNull(environment.getProperty("API_SECRET")))))
                .build();

        // CORS 요청 허용
        List<CORSRule.AllowedMethods> allowedMethods = new ArrayList<>();
        allowedMethods.add(CORSRule.AllowedMethods.GET);

        List<String> allowOrigins = new ArrayList<>();
        allowOrigins.add("*");
        CORSRule rule = new CORSRule()
                .withId("example-cors-rule")
                .withAllowedMethods(allowedMethods)
                .withAllowedOrigins(allowOrigins)
                .withMaxAgeSeconds(3600);

        BucketCrossOriginConfiguration corsConfiguration = new BucketCrossOriginConfiguration().withRules(rule);
        s3.setBucketCrossOriginConfiguration("hagima-voice", corsConfiguration);
        s3.setBucketCrossOriginConfiguration("hagima-face", corsConfiguration);

    }

    public void uploadProfile(String name, MultipartFile file) {
        ObjectMetadata data = new ObjectMetadata();
        data.setContentType(file.getContentType());
        data.setContentLength(file.getSize());
        try {
            PutObjectResult objectResult = s3.putObject(FACE_BUCKET, name, file.getInputStream(), data);
            System.out.format("Object %s has been created.\n", objectResult.getContentMd5());
        } catch (SdkClientException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getObjectUrl(String bucket, String name) {
        GeneratePresignedUrlRequest requestUrl =
                new GeneratePresignedUrlRequest(bucket, name, HttpMethod.GET);
        return s3.generatePresignedUrl(requestUrl).toString();
    }

    public ArrayList<MultipartFile> getProfileImages(List<String> fileNames) {
        ArrayList<MultipartFile> profileImages = new ArrayList<>();

        fileNames.forEach(name -> {
            try (S3Object s3Object = s3.getObject(FACE_BUCKET, name)) {
                InputStream inputStream = s3Object.getObjectContent();
                profileImages.add(inputStreamToMultipartFile(inputStream, name));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });

        return profileImages;
    }

    private MultipartFile inputStreamToMultipartFile(InputStream inputStream, String fileName) {
        try {
            return new MockMultipartFile("file", fileName, "image/jpeg", inputStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
