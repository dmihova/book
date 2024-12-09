package com.tinqin.academy.library.rest.controllers;

import com.tinqin.academy.library.api.APIRoutes;
import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.operations.subscription.createsubscription.CreateSubscription;
import com.tinqin.academy.library.api.operations.subscription.createsubscription.CreateSubscriptionInput;
import com.tinqin.academy.library.api.operations.subscription.createsubscription.CreateSubscriptionResult;
import com.tinqin.academy.library.api.operations.subscription.deletesubscription.DeleteSubscription;
import com.tinqin.academy.library.api.operations.subscription.deletesubscription.DeleteSubscriptionInput;
import com.tinqin.academy.library.api.operations.subscription.deletesubscription.DeleteSubscriptionResult;
import com.tinqin.academy.library.api.operations.subscription.getsubscription.GetSubscription;
import com.tinqin.academy.library.api.operations.subscription.getsubscription.GetSubscriptionInput;
import com.tinqin.academy.library.api.operations.subscription.getsubscription.GetSubscriptionResult;
import com.tinqin.academy.library.api.operations.subscription.querysubscription.QuerySubscription;
import com.tinqin.academy.library.api.operations.subscription.querysubscription.QuerySubscriptionInput;
import com.tinqin.academy.library.api.operations.subscription.querysubscription.QuerySubscriptionResult;
import com.tinqin.academy.library.rest.controllers.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.vavr.control.Either;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class SubscriptionController extends BaseController {

    private final CreateSubscription createSubscription;
    private final GetSubscription getSubscription;
    private final QuerySubscription querySubscription;
    private final DeleteSubscription deleteSubscription;

    @GetMapping(APIRoutes.GET_SUBSCRIPTION)
    public ResponseEntity<?> getSubscription(@PathVariable("subscriptionId") String subscriptionId) {
        GetSubscriptionInput input = GetSubscriptionInput
                .builder()
                .subscriptionId(subscriptionId)
                .build();

        Either<OperationError, GetSubscriptionResult> result = getSubscription.process(input);
        return mapToResponseEntity(result, HttpStatus.OK);
    }


    @GetMapping(APIRoutes.API_SUBSCRIPTION)
    public ResponseEntity<?> getSubscriptions(
            @RequestParam(value = "userId", required = false, defaultValue = "") String userId,
            @RequestParam(value = "active", required = false, defaultValue = "") boolean active
    ) {
        QuerySubscriptionInput input = QuerySubscriptionInput
                .builder()
                .userId(userId)
                .active(active)
                .build();
        Either<OperationError, QuerySubscriptionResult> result = querySubscription.process(input);
        return mapToResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(APIRoutes.DELETE_SUBSCRIPTION)
    public ResponseEntity<?> deleteSubscription(@PathVariable("subscriptionId") String subscriptionId) {

        DeleteSubscriptionInput input = DeleteSubscriptionInput
                .builder()
                .subscriptionId(subscriptionId)
                .build();

        Either<OperationError, DeleteSubscriptionResult> process = deleteSubscription.process(input);
        return mapToResponseEntity(process, HttpStatus.CREATED);
    }

    @PostMapping(APIRoutes.API_SUBSCRIPTION)
    @Operation(summary = "Create subscription ",
            description = "Create subscription and return UUID")
    public ResponseEntity<?> postSubscription(@Valid @RequestBody CreateSubscriptionInput input) {

        Either<OperationError, CreateSubscriptionResult> process = createSubscription.process(input);
        return mapToResponseEntity(process, HttpStatus.CREATED);

    }

}
