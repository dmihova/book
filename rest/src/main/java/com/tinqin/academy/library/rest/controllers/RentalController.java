package com.tinqin.academy.library.rest.controllers;


import com.tinqin.academy.library.api.APIRoutes;
import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.operations.book.getbook.GetBookInput;
import com.tinqin.academy.library.api.operations.book.getbook.GetBookResult;
import com.tinqin.academy.library.api.operations.book.getbooksbyAuthor.GetBooksByAuthorResult;
import com.tinqin.academy.library.api.operations.rental.createrental.CreateRental;
import com.tinqin.academy.library.api.operations.rental.createrental.CreateRentalInput;
import com.tinqin.academy.library.api.operations.rental.createrental.CreateRentalResult;
import com.tinqin.academy.library.api.operations.rental.getrental.GetBookRental;
import com.tinqin.academy.library.api.operations.rental.getrental.GetBookRentalInput;
import com.tinqin.academy.library.api.operations.rental.getrental.GetBookRentalResult;
import com.tinqin.academy.library.api.operations.rental.queryrental.QueryBookRental;
import com.tinqin.academy.library.api.operations.rental.queryrental.QueryBookRentalInput;
import com.tinqin.academy.library.api.operations.rental.queryrental.QueryBookRentalResult;
import com.tinqin.academy.library.api.operations.rental.rerurnRental.ReturnRentalInput;
import com.tinqin.academy.library.api.operations.rental.rerurnRental.ReturnRentalResult;
import com.tinqin.academy.library.core.processors.rental.ReturnRentalOperation;
import com.tinqin.academy.library.rest.controllers.base.BaseController;
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

import javax.management.Query;


@RestController
@RequiredArgsConstructor
public class RentalController extends BaseController {

    private final CreateRental createRental;
    private final QueryBookRental queryBookRental;
    private final GetBookRental getBookRental;
    private final ReturnRentalOperation returnRentalOperation;


    @PostMapping(APIRoutes.API_RENTAL_RENT)
    @Operation(summary = "Rent book ",
            description = "Rent a book and return UUID")
    public ResponseEntity<?> rentABook(@Valid @RequestBody CreateRentalInput input) {

        Either<OperationError, CreateRentalResult> process = createRental.process(input);
        return mapToResponseEntity(process, HttpStatus.CREATED);

    }

    @GetMapping(APIRoutes.API_RENTAL)
    public ResponseEntity<?> getRentals(@RequestParam(name = "userId", required = false, defaultValue = "") String userId,
                                        @RequestParam(name = "bookId", required = false, defaultValue = "") String bookId,
                                        @RequestParam(name = "subscriptionId", required = false, defaultValue = "") String subscriptionId,
                                        @SortDefault(sort = "start_date", direction = Sort.Direction.ASC)
                                        @PageableDefault(page = 0, size = 10
                                        ) Pageable pageable
    ) {

        // PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("start_date").ascending());
        QueryBookRentalInput input = QueryBookRentalInput
                .builder()
                .userId(userId)
                .subscriptionId(subscriptionId)
                .bookId(bookId)
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

}
