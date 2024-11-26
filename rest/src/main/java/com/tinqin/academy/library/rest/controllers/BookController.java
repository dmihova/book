package com.tinqin.academy.library.rest.controllers;


import com.tinqin.academy.library.api.APIRoutes;
import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.operations.deletebook.DeleteBook;
import com.tinqin.academy.library.api.operations.deletebook.DeleteBookInput;
import com.tinqin.academy.library.api.operations.deletebook.DeleteBookOutput;
import com.tinqin.academy.library.api.operations.getbook.GetBook;
import com.tinqin.academy.library.api.operations.getbook.GetBookInput;
import com.tinqin.academy.library.api.operations.getbook.GetBookOutput;
import com.tinqin.academy.library.api.operations.createbook.CreateBook;
import com.tinqin.academy.library.api.operations.createbook.CreateBookInput;
import com.tinqin.academy.library.api.operations.createbook.CreateBookOutput;
import com.tinqin.academy.library.api.operations.querybook.QueryBook;
import com.tinqin.academy.library.api.operations.querybook.QueryBookInput;
import com.tinqin.academy.library.api.operations.querybook.QueryBookOutput;
import io.vavr.control.Either;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController

public class BookController extends BaseController {

    private final GetBook getBook;
    private final QueryBook queryBook;
    private final CreateBook createBook;
    private final DeleteBook deleteBook;


    @GetMapping(APIRoutes.GET_BOOK)
    public ResponseEntity<?> getBook(@PathVariable("bookId") String bookId) {
        GetBookInput input = GetBookInput
                .builder()
                .bookId(bookId)
                .build();

        Either<OperationError, GetBookOutput> result = getBook.process(input);
        return mapToResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(APIRoutes.API_BOOK)
    public ResponseEntity<?> getBooks(
            @RequestParam(value = "title%", required = false, defaultValue = "") String title,
            @RequestParam(value = "authorId", required = false, defaultValue = "") String authorId,
            @RequestParam(value = "authorFirstName", required = false, defaultValue = "") String authorFirstName,
            @RequestParam(value = "authorLastName", required = false, defaultValue = "") String authorLastName
    ) {

        QueryBookInput input = QueryBookInput
                .builder()
                .title(title)
                .authorId(authorId)
                .authorFirstName(authorFirstName)
                .authorLastName(authorLastName)
                .build();
        Either<OperationError, QueryBookOutput> result = queryBook.process(input);
        return mapToResponseEntity(result, HttpStatus.OK);


    }

    @PostMapping(APIRoutes.API_BOOK)
    public ResponseEntity<?> postBook(@Valid @RequestBody CreateBookInput input) {
        Either<OperationError, CreateBookOutput> process = createBook.process(input);
        return mapToResponseEntity(process, HttpStatus.CREATED);

    }

    @DeleteMapping(APIRoutes.DELETE_BOOK)
    public ResponseEntity<?> deleteBook(@PathVariable("bookId") String bookId) {

        DeleteBookInput input = DeleteBookInput
                .builder()
                .bookId(bookId)
                .build();
        Either<OperationError, DeleteBookOutput> process = deleteBook.process(input);
        return mapToResponseEntity(process, HttpStatus.CREATED);

    }

}
