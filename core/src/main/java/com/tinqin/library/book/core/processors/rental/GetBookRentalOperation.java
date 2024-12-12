package com.tinqin.library.book.core.processors.rental;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.rental.getrental.GetBookRental;
import com.tinqin.library.book.api.operations.rental.getrental.GetBookRentalInput;
import com.tinqin.library.book.api.operations.rental.getrental.GetBookRentalResult;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.errorhandler.exceptions.BusinessException;
import com.tinqin.library.book.persistence.models.BookRental;
import com.tinqin.library.book.persistence.repositories.BookRentalRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.tinqin.library.book.api.ValidationMessages.BOOK_RENTAL_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class GetBookRentalOperation implements GetBookRental {
    private final BookRentalRepository bookRentalRepository;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, GetBookRentalResult> process(GetBookRentalInput input) {
        return fetchBookRental(input)
                .map(this::convertBookRentalGetBookRentalResult)
                .toEither()
                .mapLeft(errorHandler::handle);
    }


    private Try<BookRental> fetchBookRental(GetBookRentalInput input) {
        return Try.of(() -> bookRentalRepository.findById(UUID.fromString(input.getRentalId()))
                .orElseThrow(() -> new BusinessException(BOOK_RENTAL_NOT_FOUND)));
    }

    private GetBookRentalResult convertBookRentalGetBookRentalResult(BookRental bookRental) {
        return GetBookRentalResult
                .builder()
                .id(bookRental.getId().toString())
                .startDate(bookRental.getStartDate())
                .endDate(bookRental.getEndDate())
                .book(new GetBookRentalResult.GetBookRentalBook(
                        bookRental.getBook().getId().toString(),
                        bookRental.getBook().getTitle()))
                .user(new GetBookRentalResult.GetBookRentalUser(
                        bookRental.getUser().getId().toString(),
                        bookRental.getUser().getFirstName(),
                        bookRental.getUser().getLastName()))
                .subscription(new GetBookRentalResult.GetBookRentalSubscription(
                        bookRental.getSubscription().getId().toString(),
                        bookRental.getSubscription().getEndDate()))
                .build();
    }
}
