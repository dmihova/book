package com.tinqin.library.book.rest.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqin.library.book.api.operations.book.createbook.CreateBookInput;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.repositories.AuthorRepository;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.List;

import static com.tinqin.library.book.api.APIRoutes.API_BOOK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
@Testcontainers
class BookControllerContaintersTest {

    private static final String POSTGRES_VERSION = "postgres:15.7";
    private static final String CONTAINER_LABEL = "book";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorRepository authorRepository;

    @SpyBean
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    private static PostgreSQLContainer<?> postgres;

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    static {
        postgres = new PostgreSQLContainer<>(DockerImageName.parse(POSTGRES_VERSION))
                .withDatabaseName(CONTAINER_LABEL)
                .withUsername(CONTAINER_LABEL)
                .withPassword(CONTAINER_LABEL);

        postgres.start();
    }

    //    @BeforeAll
//    static void initSetup() {
//        postgres = new PostgreSQLContainer<>(DockerImageName.parse(POSTGRES_VERSION))
//                .withDatabaseName(CONTAINER_LABEL)
//                .withUsername(CONTAINER_LABEL)
//                .withPassword(CONTAINER_LABEL);
//
//        postgres.start();
//    }
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        registry.add("spring.flyway.url", postgres::getJdbcUrl);
        registry.add("spring.flyway.user", postgres::getUsername);
        registry.add("spring.flyway.password", postgres::getPassword);
    }

    @Test
    @SneakyThrows
    void returns201whenSuccessful() {

        Author author = Author
                .builder()
                .firstName("Ivan")
                .lastName("Vazov")
                .build();

        Author savdedAuthor = authorRepository.save(author);

        CreateBookInput book = CreateBookInput
                .builder()
                .authorIds(List.of(savdedAuthor.getId().toString()))
                .title("Pod Igoto")
                .pages("200")
                .price(BigDecimal.TEN)
                .build();

        mockMvc.perform(post(API_BOOK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(bookRepository.findAll().getFirst().getId().toString()));

        System.out.println();
    }

}