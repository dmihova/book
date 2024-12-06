package com.tinqin.academy.library.core.processors.rental;

import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.operations.rental.rerurnRental.ReturnRental;
import com.tinqin.academy.library.api.operations.rental.rerurnRental.ReturnRentalInput;
import com.tinqin.academy.library.api.operations.rental.rerurnRental.ReturnRentalResult;
import com.tinqin.academy.library.core.errorhandler.base.ErrorHandler;
import com.tinqin.academy.library.core.errorhandler.exceptions.BusinessException;
import com.tinqin.academy.library.persistence.models.Book;
import com.tinqin.academy.library.persistence.models.BookRental;
import com.tinqin.academy.library.persistence.models.User;
import com.tinqin.academy.library.persistence.repositories.BookRentalRepository;
import com.tinqin.academy.library.persistence.repositories.BookRepository;
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
