package com.tinqin.academy.library.core.processors.author;

import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.operations.author.createauthor.CreateAuthor;
import com.tinqin.academy.library.api.operations.author.createauthor.CreateAuthorInput;
import com.tinqin.academy.library.api.operations.author.createauthor.CreateAuthorResult;
import com.tinqin.academy.library.core.errorhandler.base.ErrorHandler;
import com.tinqin.academy.library.core.errorhandler.exceptions.BusinessException;
import com.tinqin.academy.library.domain.clients.ReportingClient;
import com.tinqin.academy.library.persistence.models.Author;
import com.tinqin.academy.library.persistence.repositories.AuthorRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAuthorOperation implements CreateAuthor {

    private final AuthorRepository authorRepository;
    private final ConversionService conversionService;
    private final ErrorHandler errorHandler;
    private final ReportingClient reportingClient;

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
            reportingClient.createRecord();
            return input;
        }) ;
    }

    private Try<CreateAuthorResult> saveAuthor(Author author) {
        return Try.of(() -> authorRepository.save(author))
                .map(savedAuthor -> CreateAuthorResult.builder()
                        .id(savedAuthor.getId())
                        .build());
    }
}
