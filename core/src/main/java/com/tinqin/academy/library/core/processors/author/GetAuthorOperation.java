package com.tinqin.academy.library.core.processors.author;

import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.operations.getauthor.GetAuthor;
import com.tinqin.academy.library.api.operations.getauthor.GetAuthorInput;
import com.tinqin.academy.library.api.operations.getauthor.GetAuthorOutput;
import com.tinqin.academy.library.api.operations.getbook.GetBookInput;
import com.tinqin.academy.library.core.errorhandler.base.ErrorHandler;
import com.tinqin.academy.library.persistence.models.Author;
import com.tinqin.academy.library.persistence.models.Book;
import com.tinqin.academy.library.persistence.repositories.AuthorRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.tinqin.academy.library.api.ValidationMessages.AUTHOR_NOT_FOUND;
import static com.tinqin.academy.library.api.ValidationMessages.BOOK_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class GetAuthorOperation implements GetAuthor {

    private final AuthorRepository authorRepository;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, GetAuthorOutput> process(GetAuthorInput input) {
        return fetchAuthor(input)
                .map(this::convertAuthorToGetAuthorOutput)
                .toEither()
                .mapLeft(errorHandler::handle);
    }


    private Try<Author> fetchAuthor(GetAuthorInput input) {
        return Try.of(() -> authorRepository
                .findById(UUID.fromString(input.getAuthorId()))
                .orElseThrow(() -> new RuntimeException(AUTHOR_NOT_FOUND)));

    }

    private GetAuthorOutput convertAuthorToGetAuthorOutput(Author author) {
        return GetAuthorOutput.builder()
                .authorId(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
    }


}
