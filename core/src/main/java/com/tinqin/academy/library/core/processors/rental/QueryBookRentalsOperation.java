package com.tinqin.academy.library.core.processors.rental;

import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.models.rental.BookRentalModel;
import com.tinqin.academy.library.api.operations.rental.queryrental.QueryBookRental;
import com.tinqin.academy.library.api.operations.rental.queryrental.QueryBookRentalInput;
import com.tinqin.academy.library.api.operations.rental.queryrental.QueryBookRentalResult;
import com.tinqin.academy.library.core.errorhandler.base.ErrorHandler;
import com.tinqin.academy.library.core.errorhandler.exceptions.BusinessException;
import com.tinqin.academy.library.persistence.models.*;
import com.tinqin.academy.library.persistence.repositories.*;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.tinqin.academy.library.api.ValidationMessages.*;

@Service
@RequiredArgsConstructor
public class QueryBookRentalsOperation implements QueryBookRental {

    private final BookRentalRepository bookRentalRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ErrorHandler errorHandler;


    @Override
    public Either<OperationError, QueryBookRentalResult> process(QueryBookRentalInput input) {
        return Try.of(() -> {
            User user = null;
            if (!input.getUserId().isEmpty()) {
                user = userRepository.findById(UUID.fromString(input.getUserId())).orElseThrow(() -> new BusinessException(USER_NOT_FOUND));
            }
            Book book = null;
            if (!input.getBookId().isEmpty()) {
                book = bookRepository.findById(UUID.fromString(input.getBookId())).orElseThrow(() -> new BusinessException(BOOK_NOT_FOUND));
            }

            Subscription subscription = null;
            if (!input.getSubscriptionId().isEmpty()) {
                subscription = subscriptionRepository.findById(UUID.fromString(input.getSubscriptionId()))
                        .orElseThrow(() -> new BusinessException(SUBSCRIPTION_NOT_FOUND));
            }

            List<BookRental> entityBooks = bookRentalRepository.findAll();

            List<BookRentalModel> bookRentalModels = entityBooks
                    .stream()
                    .map(this::convertBookRentalToBookRentalModel)
                    .toList();

            return QueryBookRentalResult
                    .builder()
                    .bookRentalModels(bookRentalModels)
                    .build();
        }).toEither().mapLeft(errorHandler::handle);

    }

    private BookRentalModel convertBookRentalToBookRentalModel(BookRental bookRental) {
        return BookRentalModel
                .builder()
                .id(bookRental.getId().toString())
                .userId(bookRental.getUser().getId().toString())
                .bookId(bookRental.getBook().getId().toString())
                .subscriptionId(bookRental.getSubscription().getId().toString())
                .startDate(bookRental.getStartDate())
                .endDate(bookRental.getEndDate())
                .build();
    }


}
