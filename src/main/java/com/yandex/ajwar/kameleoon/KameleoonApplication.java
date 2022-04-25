package com.yandex.ajwar.kameleoon;

import com.yandex.ajwar.kameleoon.configs.repositories.CustomSimpleJpaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.TimeZone;

@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.yandex.ajwar.kameleoon.repositories"},
        repositoryBaseClass = CustomSimpleJpaRepository.class)
@EntityScan("com.yandex.ajwar.kameleoon.entities.sql")
@SpringBootApplication
public class KameleoonApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(KameleoonApplication.class, args);
    }
}
