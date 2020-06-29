package com.detector.detector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class DetectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DetectorApplication.class, args);
	}

}
