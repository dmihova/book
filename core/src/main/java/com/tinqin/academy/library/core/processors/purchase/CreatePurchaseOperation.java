package com.tinqin.academy.library.core.processors.purchase;

import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.operations.purchase.createpurchase.CreatePurchase;
import com.tinqin.academy.library.api.operations.purchase.createpurchase.CreatePurchaseInput;
import com.tinqin.academy.library.api.operations.purchase.createpurchase.CreatePurchaseResult;
import com.tinqin.academy.library.api.operations.rental.createrental.CreateRental;
import com.tinqin.academy.library.api.operations.rental.createrental.CreateRentalInput;
import com.tinqin.academy.library.api.operations.rental.createrental.CreateRentalResult;
import com.tinqin.academy.library.core.errorhandler.base.ErrorHandler;
import com.tinqin.academy.library.core.errorhandler.exceptions.BusinessException;
import com.tinqin.academy.library.persistence.models.*;
import com.tinqin.academy.library.persistence.repositories.*;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

import static com.tinqin.academy.library.api.ValidationMessages.*;


@Service
@RequiredArgsConstructor
public class CreatePurchaseOperation implements CreatePurchase {
    private final PurchaseRepository purchaseRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, CreatePurchaseResult> process(CreatePurchaseInput input) {
        return fetchUser(input.getUserId())
                .flatMap(user -> creatPurchase(input.getBookId(), user))
                .flatMap(this::convertToResult)
                .toEither()
                .mapLeft(errorHandler::handle);
    }


    private Try<User> fetchUser(String id) {
        return Try.of(() -> userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND)));
    }


    private Try<Purchase> creatPurchase(String bookId, User user) {
        return Try.of(() -> {
            Book book = bookRepository
                    .findById(UUID.fromString(bookId))
                    .orElseThrow(() -> new BusinessException(BOOK_NOT_FOUND));

            if (book.getStock()<1)
                     throw  new BusinessException(BOOK_OUT_OF_STOCK);

            Purchase newRental = Purchase
                    .builder()
                    .book(book)
                    .user(user)
                    .price(book.getPrice())
                    .purchaseDate(LocalDate.now())
                    .build();
            book.setStock(book.getStock() - 1);
            bookRepository.save(book);
            return purchaseRepository.save(newRental);
        }) ;
    }


    private Try<CreatePurchaseResult> convertToResult(Purchase purchase) {
        return Try.of(() -> CreatePurchaseResult.builder()
                .id(purchase.getId())
                .price(purchase.getPrice())
                .build());
    }


}
