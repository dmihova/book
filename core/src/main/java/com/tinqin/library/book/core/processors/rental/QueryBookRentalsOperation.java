package com.tinqin.library.book.core.processors.rental;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.models.rental.BookRentalModel;
import com.tinqin.library.book.api.operations.rental.queryrental.QueryBookRental;
import com.tinqin.library.book.api.operations.rental.queryrental.QueryBookRentalInput;
import com.tinqin.library.book.api.operations.rental.queryrental.QueryBookRentalResult;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.errorhandler.exceptions.BusinessException;
import com.tinqin.library.book.core.specification.BookRentalSpecification;
import com.tinqin.library.book.core.specification.filtermodel.BookRentalFilter;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.models.BookRental;
import com.tinqin.library.book.persistence.models.Subscription;
import com.tinqin.library.book.persistence.models.User;
import com.tinqin.library.book.persistence.repositories.BookRentalRepository;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import com.tinqin.library.book.persistence.repositories.SubscriptionRepository;
import com.tinqin.library.book.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.tinqin.library.book.api.ValidationMessages.*;

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
                user = userRepository
                        .findById(UUID.fromString(input.getUserId()))
                        .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));
            }
            Book book = null;
            if (!input.getBookId().isEmpty()) {
                book = bookRepository
                        .findById(UUID.fromString(input.getBookId()))
                        .orElseThrow(() -> new BusinessException(BOOK_NOT_FOUND));
            }

            Subscription subscription = null;
            if (!input.getSubscriptionId().isEmpty()) {
                subscription = subscriptionRepository
                        .findById(UUID.fromString(input.getSubscriptionId()))
                        .orElseThrow(() -> new BusinessException(SUBSCRIPTION_NOT_FOUND));
            }

            BookRentalFilter filter = BookRentalFilter
                    .builder()
                    .book(book)
                    .user(user)
                    .subscription(subscription)
                    .returned(input.getReturned())
                    .build();
            ;

            Specification<BookRental> specification = BookRentalSpecification.getSpecification(filter);
            List<BookRental> entityBooks = bookRentalRepository
                    .findAll(specification, input.getPageable()).toList();

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
