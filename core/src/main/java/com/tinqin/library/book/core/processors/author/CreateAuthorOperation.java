package com.tinqin.library.book.core.processors.author;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.author.createauthor.CreateAuthor;
import com.tinqin.library.book.api.operations.author.createauthor.CreateAuthorInput;
import com.tinqin.library.book.api.operations.author.createauthor.CreateAuthorResult;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.domain.clients.ReportingClient;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.repositories.AuthorRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAuthorOperation implements CreateAuthor {

    private final AuthorRepository authorRepository;
    private final ConversionService conversionService;
    private final ErrorHandler errorHandler;
    private final ReportingClient reportingClient;

    @Value("${reporting.active}")
    private boolean reportingEnabled;


    @Override
    public Either<OperationError, CreateAuthorResult> process(CreateAuthorInput input) {
        return createRecord(input)
                .map(inputFromRecord -> conversionService.convert(inputFromRecord, Author.class))
                .flatMap(this::saveAuthor)
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private Try<CreateAuthorInput> createRecord(CreateAuthorInput input) {
        return Try.of(() -> {
            if (reportingEnabled) {
                reportingClient.createRecord();
            }
            return input;
        });
    }

    private Try<CreateAuthorResult> saveAuthor(Author author) {
        return Try.of(() -> authorRepository.save(author))
                .map(savedAuthor -> CreateAuthorResult.builder()
                        .id(savedAuthor.getId())
                        .build());
    }
}
