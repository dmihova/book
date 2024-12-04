package com.tinqin.academy.library.core.processors.subscription;

import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.operations.subscription.createsubscription.CreateSubscription;
import com.tinqin.academy.library.api.operations.subscription.createsubscription.CreateSubscriptionInput;
import com.tinqin.academy.library.api.operations.subscription.createsubscription.CreateSubscriptionResult;
import com.tinqin.academy.library.core.errorhandler.base.ErrorHandler;
import com.tinqin.academy.library.persistence.models.Subscription;
import com.tinqin.academy.library.persistence.models.User;
import com.tinqin.academy.library.persistence.repositories.SubscriptionRepository;
import com.tinqin.academy.library.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static com.tinqin.academy.library.api.ValidationMessages.USER_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class CreateSubscriptionOperation implements CreateSubscription {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, CreateSubscriptionResult> process(CreateSubscriptionInput input) {
        return fetchUser(input)
                .flatMap(userEntity -> createSubscription(input, userEntity))
                .flatMap(this::convertToResult)
                .toEither()
                .mapLeft(errorHandler::handle);
    }


    private Try<Subscription> createSubscription(CreateSubscriptionInput input, User userEntity) {
        return Try.of(() -> {
            Optional<Subscription> subscriptionOptional =
                    subscriptionRepository.findByUserAndEndDateGreaterThanEqual(userEntity, LocalDate.now());
            Subscription newSubscription = subscriptionOptional.orElseGet(() -> Subscription
                    .builder()
                    .user(userEntity)
                    .canRent(true)
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now())
                    .build());
            newSubscription.setCanRent(true);
            newSubscription.setEndDate(newSubscription.getEndDate().plusDays(30));
            return subscriptionRepository.save(newSubscription);
        });
    }

    private Try<User> fetchUser(CreateSubscriptionInput input) {
        return Try.of(() -> userRepository.findById(UUID.fromString(input.getUserId()))
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND)));
    }


    private Try<CreateSubscriptionResult> convertToResult(Subscription subscription) {
        return Try.of(() -> CreateSubscriptionResult.builder()
                .id(subscription.getId())
                .endDate(subscription.getEndDate())
                .startDate(subscription.getStartDate())
                .build());
    }


}
