package com.tinqin.academy.library.core.processors.purchase;

import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.operations.purchase.getpurchase.GetPurchase;
import com.tinqin.academy.library.api.operations.purchase.getpurchase.GetPurchaseInput;
import com.tinqin.academy.library.api.operations.purchase.getpurchase.GetPurchaseResult;
import com.tinqin.academy.library.api.operations.rental.getrental.GetBookRentalInput;
import com.tinqin.academy.library.api.operations.rental.getrental.GetBookRentalResult;
import com.tinqin.academy.library.core.errorhandler.base.ErrorHandler;
import com.tinqin.academy.library.core.errorhandler.exceptions.BusinessException;
import com.tinqin.academy.library.persistence.models.BookRental;
import com.tinqin.academy.library.persistence.models.Purchase;
import com.tinqin.academy.library.persistence.repositories.BookRentalRepository;
import com.tinqin.academy.library.persistence.repositories.PurchaseRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.tinqin.academy.library.api.ValidationMessages.BOOK_RENTAL_NOT_FOUND;
import static com.tinqin.academy.library.api.ValidationMessages.PURCHASE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class GetPurchaseOperation implements GetPurchase {
    private final PurchaseRepository purchaseRepository;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, GetPurchaseResult> process(GetPurchaseInput input) {
        return fetchPurchase(input)
                .map(this::convertPurchaseToGetPurchaseResult)
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private Try<Purchase> fetchPurchase(GetPurchaseInput input) {
        return Try.of(() -> purchaseRepository.findById(UUID.fromString(input.getPurchaseId()))
                .orElseThrow(() -> new BusinessException(PURCHASE_NOT_FOUND)));
    }

    private GetPurchaseResult convertPurchaseToGetPurchaseResult(Purchase purchase) {
        return GetPurchaseResult
                .builder()
                .purchaseId(purchase.getId())
                .purchaseDate(purchase.getPurchaseDate())
                .price(purchase.getPrice())
                .book(new GetPurchaseResult.GetPurchaseBook(
                        purchase.getBook().getId().toString(),
                        purchase.getBook().getTitle()))
                .user(new GetPurchaseResult.GetPurchaseUser(
                        purchase.getUser().getId().toString(),
                        purchase.getUser().getFirstName(),
                        purchase.getUser().getLastName()))
                .build();
    }

}
