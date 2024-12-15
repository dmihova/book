package com.tinqin.library.book.core.processors.purchase;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.purchase.createpurchase.CreatePurchase;
import com.tinqin.library.book.api.operations.purchase.createpurchase.CreatePurchaseInput;
import com.tinqin.library.book.api.operations.purchase.createpurchase.CreatePurchaseResult;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.errorhandler.exceptions.BusinessException;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.models.Purchase;
import com.tinqin.library.book.persistence.models.User;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import com.tinqin.library.book.persistence.repositories.PurchaseRepository;
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
