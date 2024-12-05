package com.tinqin.academy.library.api.operations.subscription.createsubscription;


import com.tinqin.academy.library.api.base.Processor;
import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.operations.rental.createrental.CreateRentalInput;
import com.tinqin.academy.library.api.operations.rental.createrental.CreateRentalResult;
import io.vavr.control.Either;

public interface CreateSubscription extends Processor<CreateSubscriptionResult, CreateSubscriptionInput> {
    Either<OperationError, CreateSubscriptionResult> process(CreateSubscriptionInput input);
}
