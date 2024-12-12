package com.tinqin.library.book.core.processors.rental;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.rental.rerurnRental.ReturnRental;
import com.tinqin.library.book.api.operations.rental.rerurnRental.ReturnRentalInput;
import com.tinqin.library.book.api.operations.rental.rerurnRental.ReturnRentalResult;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.errorhandler.exceptions.BusinessException;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.models.BookRental;
import com.tinqin.library.book.persistence.models.User;
import com.tinqin.library.book.persistence.repositories.BookRentalRepository;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import com.tinqin.library.book.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

import static com.tinqin.library.book.api.ValidationMessages.*;

@Service
@RequiredArgsConstructor
public class ReturnRentalOperation implements ReturnRental {
    private final BookRentalRepository bookRentalRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, ReturnRentalResult> process(ReturnRentalInput input) {
        return Try.of(() -> {
                    User userEntity = userRepository.findById(UUID.fromString(input.getUserId()))
                            .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));

                    Book bookEntity = bookRepository.findById(UUID.fromString(input.getBookId()))
                            .orElseThrow(() -> new BusinessException(BOOK_NOT_FOUND));

                    BookRental bookRentalEntity = bookRentalRepository
                            .findByBookAndUserAndEndDate(bookEntity, userEntity, null)
                            .orElseThrow(() -> new BusinessException(BOOK_RENTAL_NOT_FOUND));

                    bookRentalEntity.setEndDate(LocalDate.now());
                    bookEntity.setStock(bookEntity.getStock()+1);
                    bookRentalRepository.save(bookRentalEntity);
                    bookRepository.save(bookEntity);

                    return ReturnRentalResult
                            .builder()
                            .id(bookRentalEntity.getId())
                            .build();
                })
                .toEither()
                .mapLeft(errorHandler::handle);
    }

}
