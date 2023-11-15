package com.example.hagimabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class HagimaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HagimaBackendApplication.class, args);
	}

}
