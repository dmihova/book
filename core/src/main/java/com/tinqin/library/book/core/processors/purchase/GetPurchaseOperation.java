package com.tinqin.library.book.core.processors.purchase;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.purchase.getpurchase.GetPurchase;
import com.tinqin.library.book.api.operations.purchase.getpurchase.GetPurchaseInput;
import com.tinqin.library.book.api.operations.purchase.getpurchase.GetPurchaseResult;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.errorhandler.exceptions.BusinessException;
import com.tinqin.library.book.persistence.models.Purchase;
import com.tinqin.library.book.persistence.repositories.PurchaseRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.tinqin.library.book.api.ValidationMessages.PURCHASE_NOT_FOUND;

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
