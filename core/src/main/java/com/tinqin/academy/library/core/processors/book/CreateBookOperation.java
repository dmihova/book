package com.tinqin.academy.library.core.processors.book;

import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.operations.createbook.CreateBook;
import com.tinqin.academy.library.api.operations.createbook.CreateBookInput;
import com.tinqin.academy.library.api.operations.createbook.CreateBookResult;
import com.tinqin.academy.library.core.errorhandler.base.ErrorHandler;
import com.tinqin.academy.library.core.errorhandler.exceptions.BusinessException;
import com.tinqin.academy.library.persistence.models.Author;
import com.tinqin.academy.library.persistence.models.Book;
import com.tinqin.academy.library.persistence.repositories.AuthorRepository;
import com.tinqin.academy.library.persistence.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.tinqin.academy.library.api.ValidationMessages.AUTHOR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CreateBookOperation implements CreateBook {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ConversionService conversionService;
    private final ErrorHandler errorHandler;

    @Override
    public  Either<OperationError, CreateBookResult>  process(CreateBookInput input) {
          return getAuthor(input)
                .flatMap(author -> createBook(input, author))
                .flatMap(this::saveBook)
                .toEither()
                .mapLeft(errorHandler::handle) ;
    }

    private Try<Author> getAuthor(CreateBookInput input) {
        return Try.of(() -> UUID.fromString(input.getAuthorId()))
                .flatMap(authorId -> Try.of(() -> authorRepository.findById(authorId)
                        .orElseThrow(() -> new BusinessException(AUTHOR_NOT_FOUND))));
    }

    private Try<Book> createBook(CreateBookInput input, Author author) {
        return Try.of(() -> conversionService.convert(input, Book.class));
    }

    private Try<CreateBookResult> saveBook(Book book) {
        return Try.of(() -> bookRepository.save(book))
                .map(savedBook -> CreateBookResult.builder()
                        .id(savedBook.getId())
                        .build());
    }

}
