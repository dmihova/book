package com.tinqin.library.book.api.operations.subscription.createsubscription;


import com.tinqin.library.book.api.base.Processor;
import com.tinqin.library.book.api.errors.OperationError;
import io.vavr.control.Either;

public interface CreateSubscription extends Processor<CreateSubscriptionResult, CreateSubscriptionInput> {
    Either<OperationError, CreateSubscriptionResult> process(CreateSubscriptionInput input);
}
