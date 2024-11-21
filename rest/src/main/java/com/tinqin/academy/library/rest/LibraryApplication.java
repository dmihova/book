package com.tinqin.academy.library.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.tinqin.academy.library")
@EntityScan("com.tinqin.academy.library.persistence.models")
@EnableJpaRepositories(basePackages = "com.tinqin.academy.library.persistence.repositories")
public class LibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

}
