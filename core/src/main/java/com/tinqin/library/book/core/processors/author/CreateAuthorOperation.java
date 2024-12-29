package com.tinqin.library.book.core.processors.author;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.author.createauthor.CreateAuthor;
import com.tinqin.library.book.api.operations.author.createauthor.CreateAuthorInput;
import com.tinqin.library.book.api.operations.author.createauthor.CreateAuthorResult;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.domain.clients.ReportingClient;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.repositories.AuthorRepository;
import com.tinqin.library.reporting.kafkaexport.KafkaProducerService;
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
    private final KafkaProducerService kafkaProducerService;
    private final ReportingClient reportingClient;


    @Value("${reporting.feign.active}")
    private boolean reportingFeignEnabled;
    @Value("${reporting.kafka.active}")
    private boolean reportingKafkaEnabled;


    @Override
    public Either<OperationError, CreateAuthorResult> process(CreateAuthorInput input) {
        return saveAuthor(input)
                .toEither()
                .mapLeft(errorHandler::handle);
    }


    private Try<CreateAuthorResult> saveAuthor(CreateAuthorInput input) {
        return Try.of(() -> {
            Author newAuthor = conversionService.convert(input, Author.class);
            assert newAuthor != null;
            Author savedAuthor = authorRepository.save(newAuthor);
            if (reportingKafkaEnabled) {
                reportingClient.createRecord();
            }

            if (reportingFeignEnabled) {
                kafkaProducerService.createAuthorRecord(savedAuthor.getId());
            }

            return CreateAuthorResult.builder()
                    .id(savedAuthor.getId())
                    .build();
        });
    }
}
