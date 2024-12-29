package com.tinqin.library.book.rest.controllers;



import com.tinqin.library.book.api.APIRoutes;
import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.author.createauthor.CreateAuthor;
import com.tinqin.library.book.api.operations.author.createauthor.CreateAuthorInput;
import com.tinqin.library.book.api.operations.author.createauthor.CreateAuthorResult;
import com.tinqin.library.book.api.operations.author.getauthor.GetAuthor;
import com.tinqin.library.book.api.operations.author.getauthor.GetAuthorInput;
import com.tinqin.library.book.api.operations.author.getauthor.GetAuthorResult;
import com.tinqin.library.book.api.operations.author.queryauthor.QueryAuthor;
import com.tinqin.library.book.api.operations.author.queryauthor.QueryAuthorInput;
import com.tinqin.library.book.api.operations.author.queryauthor.QueryAuthorResult;
import com.tinqin.library.book.rest.controllers.base.BaseController;
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
            @RequestParam(name = "firstName", required = false, defaultValue = "") String firstName,
            @RequestParam(name = "lastName", required = false, defaultValue = "") String lastName
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
    @Operation(summary = "Create author",
            description = "Create author and return UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not found")})
    public ResponseEntity<?> postAuthor(@RequestBody CreateAuthorInput input) {

        Either<OperationError, CreateAuthorResult> process = createAuthor.process(input);
        return mapToResponseEntity(process,HttpStatus.CREATED);
    }

}
