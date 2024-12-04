package com.tinqin.academy.library.core.processors.subscription;

import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.models.subscription.SubscriptionModel;
import com.tinqin.academy.library.api.operations.subscription.createsubscription.CreateSubscriptionInput;
import com.tinqin.academy.library.api.operations.subscription.querysubscription.QuerySubscription;
import com.tinqin.academy.library.api.operations.subscription.querysubscription.QuerySubscriptionInput;
import com.tinqin.academy.library.api.operations.subscription.querysubscription.QuerySubscriptionResult;
import com.tinqin.academy.library.core.errorhandler.base.ErrorHandler;
import com.tinqin.academy.library.core.errorhandler.exceptions.BusinessException;
import com.tinqin.academy.library.persistence.models.Subscription;
import com.tinqin.academy.library.persistence.models.User;
import com.tinqin.academy.library.persistence.repositories.SubscriptionRepository;
import com.tinqin.academy.library.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

import static com.tinqin.academy.library.api.ValidationMessages.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class QuerySubscriptionOperation implements QuerySubscription {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final ConversionService conversionService;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, QuerySubscriptionResult> process(QuerySubscriptionInput input) {
        return getSubscriptions(input)
                .flatMap(this::convertToModel)
                .flatMap(this::convertToOutput)
                .toEither()
                .mapLeft(errorHandler::handle);

    }
    private Try<List<SubscriptionModel>> convertToModel(List<Subscription> subscriptions) {
        return Try.of(() -> {
            return subscriptions
                    .stream()
                    .map(subscriptionEntity -> conversionService.convert(subscriptionEntity, SubscriptionModel.class))
                    .toList();
        });
    }

    private Try<List<Subscription>> getSubscriptions(QuerySubscriptionInput input) {
        return Try.of(() -> {
            if (input.getUserId() != null && !input.getUserId().isEmpty()) {
                return subscriptionRepository.findByUser_Id(UUID.fromString(input.getUserId()));
            }
            return subscriptionRepository.findAll();
        });
    }


    private Try<User> fetchUser(CreateSubscriptionInput input) {
        return Try.of(() -> userRepository.findById(UUID.fromString(input.getUserId()))
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND)));
    }

    private Try<QuerySubscriptionResult> convertToOutput(List<SubscriptionModel> subscriptionModelsls) {
        return Try.of(() -> QuerySubscriptionResult.builder()
                .subscriptionList(subscriptionModelsls)
                .build());
    }
}
