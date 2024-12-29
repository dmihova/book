package com.tinqin.library.book.core.processors.book;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.book.createbook.CreateBook;
import com.tinqin.library.book.api.operations.book.createbook.CreateBookInput;
import com.tinqin.library.book.api.operations.book.createbook.CreateBookResult;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.errorhandler.exceptions.BusinessException;
import com.tinqin.library.book.domain.clients.ReportingClient;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.repositories.AuthorRepository;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import com.tinqin.library.reporting.kafkaexport.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.tinqin.library.book.api.ValidationMessages.*;

@Service
@RequiredArgsConstructor
public class CreateBookOperation implements CreateBook {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ConversionService conversionService;
    private final ErrorHandler errorHandler;
    private final ReportingClient reportingClient;
    private final KafkaProducerService kafkaProducerService;

    @Value("${reporting.feign.active}")
    private boolean reportingFeignEnabled;
    @Value("${reporting.kafka.active}")
    private boolean reportingKafkaEnabled;

    @Override
    public Either<OperationError, CreateBookResult> process(CreateBookInput input) {
        return getAuthors( input)
                .flatMap(authors -> createBook(input, authors))
                .flatMap(this::saveBook)
                .toEither()
                .mapLeft(errorHandler::handle);
    }


    private Try<List<Author>> getAuthors(CreateBookInput input) {
        int length=input.getAuthorIds().size();
        if(length==0)
            return Try.of(List::of);

        List<Author> authors = input.getAuthorIds()
                    .stream()
                    .map(UUID::fromString)
                    .map(authorRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();

        return authors.size() == length
                    ? Try.success(authors)
                    : Try.failure(new BusinessException(AUTHOR_NOT_FOUND));
    }

    private Try<Book> createBook(CreateBookInput input, List<Author> authors) {
        return Try.of(() -> Book.builder()
                .title(input.getTitle())
                .authors(authors)
                .pages(Integer.valueOf(input.getPages()))
                .price(input.getPrice())
                .pricePerRental(input.getPrice())
                .createdOn(LocalDateTime.now())
                .stock(10)
                .isDeleted(false)
                .build());

    }

    private Try<CreateBookResult> saveBook(Book book) {
        return Try.of(() ->  bookRepository.save(book))
                .flatMap(this::createRecord)
                .map(savedBook -> CreateBookResult.builder()
                        .id(savedBook.getId())
                        .build());
    }

    private Try<Book> createRecord(Book book ) {
        return Try.of(() -> {
            if (reportingFeignEnabled) {
                reportingClient.createRecord();
            }
            if (reportingKafkaEnabled) {
                kafkaProducerService.createBookRecord(book.getId());
            }
            return book;
        });
    }


}
