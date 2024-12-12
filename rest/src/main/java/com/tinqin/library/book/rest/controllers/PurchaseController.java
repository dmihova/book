package com.tinqin.library.book.rest.controllers;

import com.tinqin.library.book.api.APIRoutes;
import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.purchase.createpurchase.CreatePurchase;
import com.tinqin.library.book.api.operations.purchase.createpurchase.CreatePurchaseInput;
import com.tinqin.library.book.api.operations.purchase.createpurchase.CreatePurchaseResult;
import com.tinqin.library.book.api.operations.purchase.getpurchase.GetPurchase;
import com.tinqin.library.book.api.operations.purchase.getpurchase.GetPurchaseInput;
import com.tinqin.library.book.api.operations.purchase.getpurchase.GetPurchaseResult;
import com.tinqin.library.book.api.operations.purchase.querypurchase.QueryPurchase;
import com.tinqin.library.book.api.operations.purchase.querypurchase.QueryPurchaseInput;
import com.tinqin.library.book.api.operations.purchase.querypurchase.QueryPurchaseResult;
import com.tinqin.library.book.rest.controllers.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.vavr.control.Either;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PurchaseController extends BaseController {
    private final CreatePurchase createPurchase;
    private final GetPurchase getPurchase;
    private final QueryPurchase queryPurchase;


    @GetMapping(APIRoutes.GET_PURCHASE)
    public ResponseEntity<?> getPurchase(@PathVariable("purchaseId") String purchaseId) {
        GetPurchaseInput input = GetPurchaseInput
                .builder()
                .purchaseId(purchaseId)
                .build();

        Either<OperationError, GetPurchaseResult> result = getPurchase.process(input);
        return mapToResponseEntity(result, HttpStatus.OK);
    }


    @GetMapping(APIRoutes.API_PURCHASE)
    public ResponseEntity<?> getPurchases(@RequestParam(name = "userId", required = false, defaultValue = "") String userId,
                                          @RequestParam(name = "bookId", required = false, defaultValue = "") String bookId,
                                          @SortDefault(sort = "purchase_date", direction = Sort.Direction.ASC)
                                          @PageableDefault(page = 0, size = 10
                                          ) Pageable pageable
    ) {

        QueryPurchaseInput input = QueryPurchaseInput
                .builder()
                .userId(userId)
                .bookId(bookId)
                .pageable(pageable)
                .build();

        Either<OperationError, QueryPurchaseResult> process = queryPurchase.process(input);
        return mapToResponseEntity(process, HttpStatus.OK);
    }

    @PostMapping(APIRoutes.API_PURCHASE)
    @Operation(summary = "Create purchase ",
            description = "Create purchase and return UUID")
    public ResponseEntity<?> postPurchase(@Valid @RequestBody CreatePurchaseInput input) {

        Either<OperationError, CreatePurchaseResult> process = createPurchase.process(input);
        return mapToResponseEntity(process, HttpStatus.CREATED);

    }

}
