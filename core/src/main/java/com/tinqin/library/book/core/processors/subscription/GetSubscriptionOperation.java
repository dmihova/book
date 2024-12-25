package com.tinqin.library.book.core.processors.subscription;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.subscription.getsubscription.GetSubscription;
import com.tinqin.library.book.api.operations.subscription.getsubscription.GetSubscriptionInput;
import com.tinqin.library.book.api.operations.subscription.getsubscription.GetSubscriptionResult;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.persistence.models.BookRental;
import com.tinqin.library.book.persistence.models.Subscription;
import com.tinqin.library.book.persistence.repositories.BookRentalRepository;
import com.tinqin.library.book.persistence.repositories.SubscriptionRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.tinqin.library.book.api.ValidationMessages.SUBSCRIPTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class GetSubscriptionOperation implements GetSubscription {
    private final SubscriptionRepository subscriptionRepository;
    private final BookRentalRepository bookRentalRepository;
    private final ErrorHandler errorHandler;



    @Override
    public Either<OperationError, GetSubscriptionResult> process(GetSubscriptionInput input) {
        return fetchSubscription(input)
                .map(this::convertSubscriptionToGetSubscriptionResult)
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private GetSubscriptionResult convertSubscriptionToGetSubscriptionResult(Subscription subscription) {
        List<BookRental> rentals =bookRentalRepository.findAllBySubscriptionId(subscription.getId());

        return GetSubscriptionResult.builder()
                .subscriptionId(subscription.getId())
                .startDate(subscription.getStartDate())
                .endDate(subscription.getEndDate())
                .canRent(subscription.getCanRent())
                .rentals(rentals
                        .stream()
                        .map(rental -> GetSubscriptionResult.GetSubscriptionRental
                                .builder()
                                .id(rental.getId().toString())
                                .bookId(rental.getBook().getId().toString())
                                .title(rental.getBook().getTitle())
                                .endDate(rental.getEndDate())
                                .startDate(rental.getStartDate())
                                .build()
                            )
                        .toList()
                )
                .user(GetSubscriptionResult.GetSubscriptionUser
                        .builder()
                        .id(subscription.getUser().getId().toString())
                        .firstName(subscription.getUser().getFirstName())
                        .lastName(subscription.getUser().getLastName())
                        .build())
                .build();
    }

    private Try<Subscription> fetchSubscription(GetSubscriptionInput input) {
        return Try.of(() -> subscriptionRepository.findById(UUID.fromString(input.getSubscriptionId()))
                .orElseThrow(() -> new RuntimeException(SUBSCRIPTION_NOT_FOUND)));
    }

}
