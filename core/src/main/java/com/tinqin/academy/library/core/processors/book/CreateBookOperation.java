package com.tinqin.academy.library.core.processors.book;

import com.tinqin.academy.library.api.operations.createbook.CreateBook;
import com.tinqin.academy.library.api.operations.createbook.CreateBookInput;
import com.tinqin.academy.library.api.operations.createbook.CreateBookOutput;
import com.tinqin.academy.library.persistence.models.Author;
import com.tinqin.academy.library.persistence.models.Book;
import com.tinqin.academy.library.persistence.repositories.AuthorRepository;
import com.tinqin.academy.library.persistence.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateBookOperation implements CreateBook {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ConversionService conversionService;


    @Override
    public CreateBookOutput process(CreateBookInput input) {
        Author author= authorRepository
                .findById(UUID.fromString(input.getAuthorId()))
                .orElseThrow(()-> new IllegalArgumentException("Author not found") );


        Book newBook =  conversionService.convert(input, Book.class);
        newBook.setAuthor(author);
        Book persistedBook =bookRepository.save(newBook);

        return CreateBookOutput.builder().id(persistedBook.getId()).build();

    }
}
