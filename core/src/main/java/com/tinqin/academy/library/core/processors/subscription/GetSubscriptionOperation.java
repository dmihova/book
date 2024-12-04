package com.tinqin.academy.library.core.processors.subscription;

import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.operations.book.getbook.GetBookInput;
import com.tinqin.academy.library.api.operations.book.getbook.GetBookResult;
import com.tinqin.academy.library.api.operations.subscription.getsubscription.GetSubscription;
import com.tinqin.academy.library.api.operations.subscription.getsubscription.GetSubscriptionInput;
import com.tinqin.academy.library.api.operations.subscription.getsubscription.GetSubscriptionResult;
import com.tinqin.academy.library.core.errorhandler.base.ErrorHandler;
import com.tinqin.academy.library.persistence.models.Book;
import com.tinqin.academy.library.persistence.models.Subscription;
import com.tinqin.academy.library.persistence.repositories.BookRepository;
import com.tinqin.academy.library.persistence.repositories.SubscriptionRepository;
import com.tinqin.academy.library.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.tinqin.academy.library.api.ValidationMessages.BOOK_NOT_FOUND;
import static com.tinqin.academy.library.api.ValidationMessages.SUBSCRIPTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class GetSubscriptionOperation implements GetSubscription {
    private final SubscriptionRepository subscriptionRepository;
    private final ErrorHandler errorHandler;



    @Override
    public Either<OperationError, GetSubscriptionResult> process(GetSubscriptionInput input) {
        return fetchSubscription(input)
                .map(this::convertSubscriptionToGetSubscriptionResult)
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private GetSubscriptionResult convertSubscriptionToGetSubscriptionResult(Subscription subscription) {
        return GetSubscriptionResult.builder()
                .subscriptionId(subscription.getId())
                .startDate(subscription.getStartDate())
                .endDate(subscription.getEndDate())
                .canRent(subscription.getCanRent())
                .userId(subscription.getUser().getId())
                .userName(subscription.getUser().getLastName())

                .build();
    }

    private Try<Subscription> fetchSubscription(GetSubscriptionInput input) {
        return Try.of(() -> subscriptionRepository.findById(UUID.fromString(input.getSubscriptionId()))
                .orElseThrow(() -> new RuntimeException(SUBSCRIPTION_NOT_FOUND)));
    }

}
