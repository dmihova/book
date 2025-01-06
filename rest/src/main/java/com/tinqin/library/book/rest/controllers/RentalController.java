package com.tinqin.library.book.rest.controllers;


import com.tinqin.library.book.api.APIRoutes;
import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.rental.createrental.CreateRental;
import com.tinqin.library.book.api.operations.rental.createrental.CreateRentalInput;
import com.tinqin.library.book.api.operations.rental.createrental.CreateRentalResult;
import com.tinqin.library.book.api.operations.rental.getrental.GetBookRental;
import com.tinqin.library.book.api.operations.rental.getrental.GetBookRentalInput;
import com.tinqin.library.book.api.operations.rental.getrental.GetBookRentalResult;
import com.tinqin.library.book.api.operations.rental.queryrental.QueryBookRental;
import com.tinqin.library.book.api.operations.rental.queryrental.QueryBookRentalInput;
import com.tinqin.library.book.api.operations.rental.queryrental.QueryBookRentalResult;
import com.tinqin.library.book.api.operations.rental.rerurnRental.ReturnRentalInput;
import com.tinqin.library.book.api.operations.rental.rerurnRental.ReturnRentalResult;
import com.tinqin.library.book.core.processors.rental.ReturnBookRentalOperation;
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

import java.time.LocalDate;


@RestController
@RequiredArgsConstructor
public class RentalController extends BaseController {

    private final CreateRental createRental;
    private final QueryBookRental queryBookRental;
    private final GetBookRental getBookRental;
    private final ReturnBookRentalOperation returnRentalOperation;


    @GetMapping(APIRoutes.API_RENTAL)
    public ResponseEntity<?> getRentals(
            @RequestParam(name = "userId", required = false ) String userId,
            @RequestParam(name = "bookId", required = false ) String bookId,
            @RequestParam(name = "subscriptionId", required = false ) String subscriptionId,
            @RequestParam(name = "returned", required = false, defaultValue = "") boolean returned,
            @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            @PageableDefault(size = 20
            ) Pageable pageable
    ) {
        QueryBookRentalInput input = QueryBookRentalInput
                .builder()
                .userId(userId)
                .subscriptionId(subscriptionId)
                .bookId(bookId)
                .returned(returned)
                .pageable(pageable)
                .build();

        Either<OperationError, QueryBookRentalResult> process = queryBookRental.process(input);
        return mapToResponseEntity(process, HttpStatus.OK);
    }

    @GetMapping(APIRoutes.GET_RENTAL)
    public ResponseEntity<?> getRental(@PathVariable("rentalId") String rentalId) {
        GetBookRentalInput input = GetBookRentalInput
                .builder()
                .rentalId(rentalId)
                .build();

        Either<OperationError, GetBookRentalResult> result = getBookRental.process(input);
        return mapToResponseEntity(result, HttpStatus.OK);
    }


    @PostMapping(APIRoutes.API_RENTAL_RETURN)
    @Operation(summary = "Return book")
    public ResponseEntity<?> returnBook(@Valid @RequestBody ReturnRentalInput input) {

        Either<OperationError, ReturnRentalResult> process = returnRentalOperation.process(input);
        return mapToResponseEntity(process, HttpStatus.CREATED);
    }


    @PostMapping(APIRoutes.API_RENTAL_RENT)
    @Operation(summary = "Rent book ",
            description = "Rent a book and return UUID")
    public ResponseEntity<?> postRental(@Valid @RequestBody CreateRentalInput input) {

        Either<OperationError, CreateRentalResult> process = createRental.process(input);
        return mapToResponseEntity(process, HttpStatus.CREATED);

    }
}
