package com.tinqin.library.book.rest.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqin.library.book.api.APIRoutes;
import com.tinqin.library.book.api.operations.book.createbook.CreateBookInput;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.repositories.AuthorRepository;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc //virtual server & virtual client
@AutoConfigureTestDatabase(connection= EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
class BookControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @SpyBean
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }
    @Test
    @SneakyThrows
    void returns201whenSuccess()  {
        Author author =  Author
                .builder()
                .firstName("John")
                .lastName("Doe")
                .build();
        Author savdedAuthor =authorRepository.save(author);


        CreateBookInput book = CreateBookInput
                .builder()
                .title("Book Title")
                .price(BigDecimal.TEN)
                .pages("500")
                .authorIds(List.of(savdedAuthor.getId().toString()))
                .build();

        String bookJson = objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(book);
        when(objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(book)).thenReturn(bookJson);
        mockMvc.perform(post(APIRoutes.API_BOOK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(bookRepository.findAll().getFirst().getId().toString()));

        System.out.println();

    }
    @Test
    @SneakyThrows
    void returns201whenSuccessful2() {

        Author author = Author
                .builder()
                .firstName("Ivan")
                .lastName("Vazov")
                .build();

        Author savdedAuthor =authorRepository.save(author);

        CreateBookInput book = CreateBookInput
                .builder()
                .authorIds(List.of(savdedAuthor.getId().toString()))
                .title("Pod Igoto")
                .pages("200")
                .price(BigDecimal.TEN)
                .build();

        mockMvc.perform(post(APIRoutes.API_BOOK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookId").value(bookRepository.findAll().getFirst().getId().toString()));

        System.out.println();
    }
}