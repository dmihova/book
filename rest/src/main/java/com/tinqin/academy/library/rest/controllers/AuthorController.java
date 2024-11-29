package com.tinqin.academy.library.rest.controllers;

import com.tinqin.academy.library.api.APIRoutes;
import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.operations.createauthor.CreateAuthor;
import com.tinqin.academy.library.api.operations.createauthor.CreateAuthorInput;
import com.tinqin.academy.library.api.operations.createauthor.CreateAuthorResult;
import com.tinqin.academy.library.api.operations.getauthor.GetAuthor;
import com.tinqin.academy.library.api.operations.getauthor.GetAuthorInput;
import com.tinqin.academy.library.api.operations.getauthor.GetAuthorResult;
import com.tinqin.academy.library.api.operations.queryauthor.QueryAuthor;
import com.tinqin.academy.library.api.operations.queryauthor.QueryAuthorInput;
import com.tinqin.academy.library.api.operations.queryauthor.QueryAuthorResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController

public class AuthorController extends BaseController {

    private final GetAuthor getAuthor;
    private final CreateAuthor createAuthor;
    private final QueryAuthor queryAuthor;


    @GetMapping(APIRoutes.GET_AUTHOR)
    public ResponseEntity<?> getAuthor(@PathVariable("authorId") String authorId) {
        GetAuthorInput getAuthorInput = GetAuthorInput
                .builder()
                .authorId(authorId)
                .build();
        Either<OperationError, GetAuthorResult> getAuthorOutput = getAuthor.process(getAuthorInput);
        return mapToResponseEntity(getAuthorOutput, HttpStatus.OK);
    }


    @GetMapping(APIRoutes.API_AUTHOR)
    public ResponseEntity<?> getAuthors(
            @RequestParam(value = "firstName", required = false, defaultValue = "") String firstName,
            @RequestParam(value = "lastName", required = false, defaultValue = "") String lastName
            //  @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            //  @RequestParam(value = "size", required = false  ) Integer size
    ) {

        QueryAuthorInput input = QueryAuthorInput
                .builder()
                .firstName(firstName.trim())
                .lastName(lastName.trim())
                .build();
        Either<OperationError, QueryAuthorResult> result = queryAuthor.process(input);
        return mapToResponseEntity(result, HttpStatus.OK);


    }

    @PostMapping(APIRoutes.API_AUTHOR)
    @Operation( summary = "Create a author",
            description = "Create a author and return UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not found")})

    public ResponseEntity<?> createAuthor(@RequestBody CreateAuthorInput input) {
        Either<OperationError, CreateAuthorResult> process = createAuthor.process(input);
        return new ResponseEntity<>(process, HttpStatus.CREATED);

    }

}
