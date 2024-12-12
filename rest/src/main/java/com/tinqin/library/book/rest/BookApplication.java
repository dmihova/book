package com.tinqin.library.book.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.tinqin.library.book")
@EntityScan("com.tinqin.library.book.persistence.models")
@EnableJpaRepositories(basePackages = "com.tinqin.library.book.persistence.repositories")
@EnableFeignClients (basePackages = "com.tinqin.library.book.domain")

public class BookApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }

}
