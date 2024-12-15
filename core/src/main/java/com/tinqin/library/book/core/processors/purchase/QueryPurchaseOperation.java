package com.tinqin.library.book.core.processors.purchase;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.models.purchase.PurchaseModel;
import com.tinqin.library.book.api.operations.purchase.querypurchase.QueryPurchase;
import com.tinqin.library.book.api.operations.purchase.querypurchase.QueryPurchaseInput;
import com.tinqin.library.book.api.operations.purchase.querypurchase.QueryPurchaseResult;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.errorhandler.exceptions.BusinessException;
import com.tinqin.library.book.core.queryfactory.PurchaseQuery;
import com.tinqin.library.book.core.queryfactory.querymodel.PurchaseFilter;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.models.Purchase;
import com.tinqin.library.book.persistence.models.User;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import com.tinqin.library.book.persistence.repositories.PurchaseRepository;
import com.tinqin.library.book.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.tinqin.library.book.api.ValidationMessages.BOOK_NOT_FOUND;
import static com.tinqin.library.book.api.ValidationMessages.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class QueryPurchaseOperation implements QueryPurchase {
    private final PurchaseRepository purchaseRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, QueryPurchaseResult> process(QueryPurchaseInput input) {

        return Try.of(() -> {

            User user = null;
            if (!input.getUserId().isEmpty()) {
                user = userRepository.findById(UUID.fromString(input.getUserId())).orElseThrow(() -> new BusinessException(USER_NOT_FOUND));
            }
            Book book = null;
            if (!input.getBookId().isEmpty()) {
                book = bookRepository.findById(UUID.fromString(input.getBookId())).orElseThrow(() -> new BusinessException(BOOK_NOT_FOUND));
            }


            PurchaseFilter filter =  PurchaseFilter
                    .builder()
                    .book(book)
                    .user(user)
                    .build();

            Specification<Purchase> specification = PurchaseQuery.getSpecification(filter);
            List<Purchase> entityPurchases = purchaseRepository
                    .findAll(specification,input.getPageable()).toList();


            List<PurchaseModel> purchaseModels = entityPurchases
                    .stream()
                    .map(this::convertPurchaseToPurchaseModel)
                    .toList();

            return QueryPurchaseResult
                    .builder()
                    .purchaseModels(purchaseModels)
                    .build();

        }).toEither().mapLeft(errorHandler::handle);
    }

    private PurchaseModel convertPurchaseToPurchaseModel(Purchase entity) {
        return PurchaseModel
                .builder()
                .id(entity.getId())
                .price(entity.getPrice())
                .purchaseDate(entity.getPurchaseDate())
                .user(new PurchaseModel.QueryPurchaseUser(
                        entity.getUser().getId(),
                        entity.getUser().getFirstName(),
                        entity.getUser().getLastName()
                ))
                .book(new PurchaseModel.QueryPurchaseBook(
                        entity.getBook().getId(),
                        entity.getBook().getTitle(),
                        entity.getBook().getPages()))
                .build();
    }
}
