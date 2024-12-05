package com.tinqin.academy.library.core.processors.rental;

import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.operations.rental.createrental.CreateRental;
import com.tinqin.academy.library.api.operations.rental.createrental.CreateRentalInput;
import com.tinqin.academy.library.api.operations.rental.createrental.CreateRentalResult;
import com.tinqin.academy.library.core.errorhandler.base.ErrorHandler;
import com.tinqin.academy.library.core.errorhandler.exceptions.BusinessException;
import com.tinqin.academy.library.persistence.models.Book;
import com.tinqin.academy.library.persistence.models.BookRental;
import com.tinqin.academy.library.persistence.models.Subscription;
import com.tinqin.academy.library.persistence.models.User;
import com.tinqin.academy.library.persistence.repositories.BookRentalRepository;
import com.tinqin.academy.library.persistence.repositories.BookRepository;
import com.tinqin.academy.library.persistence.repositories.SubscriptionRepository;
import com.tinqin.academy.library.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

import static com.tinqin.academy.library.api.ValidationMessages.*;


@Service
@RequiredArgsConstructor
public class CreateRentalOperation implements CreateRental {
    private final BookRentalRepository bookRentalRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, CreateRentalResult> process(CreateRentalInput input) {
        return fetchUser(input.getUserId())
                .flatMap(this::fetchActiveSubscription)
                .flatMap(subscription -> createRental(input.getBookId(), subscription))
                .flatMap(this::convertToResult)
                .toEither()
                .mapLeft(errorHandler::handle);
    }


    private Try<User> fetchUser(String id) {
        return Try.of(() -> userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND)));
    }


    private Try<Subscription> fetchActiveSubscription(User user) {
        return Try.of(() ->
                subscriptionRepository.findByUserAndEndDateGreaterThanEqualAndCanRent(user, LocalDate.now(), true)
                        .orElseThrow(() -> new BusinessException(SUBSCRIPTION_NOT_FOUND)));
    }


    private Try<BookRental> createRental(String bookId, Subscription subscriptionEntity) {
        return Try.of(() -> {
            Book book = bookRepository
                    .findByIdAndStockAfter(UUID.fromString(bookId), 0)
                    .orElseThrow(() -> new BusinessException(BOOK_NOT_FOUND));
            BookRental newRental = BookRental
                    .builder()
                    .book(book)
                    .subscription(subscriptionEntity)
                    .user(subscriptionEntity.getUser())
                    .startDate(LocalDate.now())
                    .build();
            book.setStock(book.getStock() - 1);
            bookRepository.save(book);
            return bookRentalRepository.save(newRental);
        });
    }


    private Try<CreateRentalResult> convertToResult(BookRental bookRental) {
        return Try.of(() -> CreateRentalResult.builder()
                .id(bookRental.getId())
                .build());
    }


}
