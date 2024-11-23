package com.daon.onjung;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.TimeZone;

@EnableAsync
@EnableJpaRepositories(basePackages = "com.daon.onjung.*.repository.mysql")
@EnableRedisRepositories(basePackages = "com.daon.onjung.*.repository.redis")
@SpringBootApplication
public class OnjungMainServerApplication {
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    public static void main(String[] args) {
        SpringApplication.run(OnjungMainServerApplication.class, args);
    }
}
