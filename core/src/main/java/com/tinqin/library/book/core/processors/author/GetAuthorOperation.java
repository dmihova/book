package com.tinqin.library.book.core.processors.author;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.author.getauthor.GetAuthor;
import com.tinqin.library.book.api.operations.author.getauthor.GetAuthorInput;
import com.tinqin.library.book.api.operations.author.getauthor.GetAuthorResult;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.errorhandler.exceptions.BusinessException;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.repositories.AuthorRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.tinqin.library.book.api.ValidationMessages.AUTHOR_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class GetAuthorOperation implements GetAuthor {

    private final AuthorRepository authorRepository;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, GetAuthorResult> process(GetAuthorInput input) {
        return fetchAuthor(input)
                .map(this::convertAuthorToGetAuthorOutput)
                .toEither()
                .mapLeft(errorHandler::handle);
    }


    private Try<Author> fetchAuthor(GetAuthorInput input) {
        return Try.of(() -> authorRepository
                .findById(UUID.fromString(input.getAuthorId()))
                .orElseThrow(() -> new BusinessException(AUTHOR_NOT_FOUND)));

    }

    private GetAuthorResult convertAuthorToGetAuthorOutput(Author author) {
        return GetAuthorResult.builder()
                .authorId(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
    }


}
