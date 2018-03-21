package com.xjtushilei.recommendintern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RecommendInternApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecommendInternApplication.class, args);
    }
}
