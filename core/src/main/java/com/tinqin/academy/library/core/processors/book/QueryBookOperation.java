package com.tinqin.academy.library.core.processors.book;


import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.models.book.BookModel;
import com.tinqin.academy.library.api.operations.createbook.CreateBookOutput;
import com.tinqin.academy.library.api.operations.querybook.QueryBook;
import com.tinqin.academy.library.api.operations.querybook.QueryBookInput;
import com.tinqin.academy.library.api.operations.querybook.QueryBookOutput;
import com.tinqin.academy.library.core.errorhandler.base.ErrorHandler;
import com.tinqin.academy.library.core.errorhandler.exceptions.BusinessException;
import com.tinqin.academy.library.persistence.models.Author;
import com.tinqin.academy.library.persistence.repositories.AuthorRepository;
import com.tinqin.academy.library.persistence.repositories.BookRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.tinqin.academy.library.api.ValidationMessages.AUTHOR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class QueryBookOperation implements QueryBook {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ConversionService conversionService;
    private final ErrorHandler errorHandler;

    //criteria builder - separate component to build complicated cases

    @Override
    public Either<OperationError, QueryBookOutput> process(QueryBookInput input) {
        if (!input.getAuthorId().isEmpty()){
            return  getAuthor(input)
                    .flatMap(author -> findBooks(author, input))
                    .toEither()
                    .mapLeft(errorHandler::handle);
        }
         return findBooks(null, input)
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private Try<QueryBookOutput> findBooks(Author author, QueryBookInput input) {
        return Try.of(() -> bookRepository
                .findBooksByAuthorAndAuthorNameAndTitle(input.getTitle(),
                        author, input.getAuthorFirstName(), input.getAuthorLastName())
                .stream()
                .map(book -> conversionService.convert(book, BookModel.class))
                .toList())
                .map(bookModelList -> QueryBookOutput.builder()
                .bookModelList(bookModelList)
                .build());

    }

    private Try<Author> getAuthor(QueryBookInput input) {
        return Try.of(() -> UUID.fromString(input.getAuthorId()))
                .flatMap(authorId -> Try.of(() -> authorRepository.findById(authorId)
                        .orElseThrow(() -> new BusinessException(AUTHOR_NOT_FOUND))));
    }
}
