package com.tinqin.academy.library.rest.controllers;


import com.tinqin.academy.library.api.APIRoutes;
import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.operations.book.deletebook.DeleteBook;
import com.tinqin.academy.library.api.operations.book.deletebook.DeleteBookInput;
import com.tinqin.academy.library.api.operations.book.deletebook.DeleteBookResult;
import com.tinqin.academy.library.api.operations.book.getbook.GetBook;
import com.tinqin.academy.library.api.operations.book.getbook.GetBookInput;
import com.tinqin.academy.library.api.operations.book.getbook.GetBookResult;
import com.tinqin.academy.library.api.operations.book.createbook.CreateBook;
import com.tinqin.academy.library.api.operations.book.createbook.CreateBookInput;
import com.tinqin.academy.library.api.operations.book.createbook.CreateBookResult;
import com.tinqin.academy.library.api.operations.book.getbooksbyAuthor.GetBooksByAuthor;
import com.tinqin.academy.library.api.operations.book.getbooksbyAuthor.GetBooksByAuthorInput;
import com.tinqin.academy.library.api.operations.book.getbooksbyAuthor.GetBooksByAuthorResult;
import com.tinqin.academy.library.api.operations.book.querybook.QueryBook;
import com.tinqin.academy.library.api.operations.book.querybook.QueryBookInput;
import com.tinqin.academy.library.api.operations.book.querybook.QueryBookResult;
import com.tinqin.academy.library.rest.controllers.base.BaseController;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.vavr.control.Either;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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
    private final GetBooksByAuthor getBooksByAuthor;

    @GetMapping(APIRoutes.GET_BOOK)
    public ResponseEntity<?> getBook(@PathVariable("bookId") String bookId) {
        GetBookInput input = GetBookInput
                .builder()
                .bookId(bookId)
                .build();

        Either<OperationError, GetBookResult> result = getBook.process(input);
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
        Either<OperationError, QueryBookResult> result = queryBook.process(input);
        return mapToResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(APIRoutes.API_BOOK_BY_AUTHOR)
    public ResponseEntity<?> getBooksByAuthor(@PathVariable(name = "authorId") String authorId,
                                              @SortDefault(sort = "title", direction = Sort.Direction.ASC)
                                              @PageableDefault(page = 0, size = 10
                                              ) Pageable pageable
    ) {

        // PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("title").ascending());
        GetBooksByAuthorInput input = GetBooksByAuthorInput
                .builder()
                .authorId(authorId)
                .pageable(pageable)
                .build();

        Either<OperationError, GetBooksByAuthorResult> process = getBooksByAuthor.process(input);

        return mapToResponseEntity(process, HttpStatus.OK);
    }


    @PostMapping(APIRoutes.API_BOOK)
    public ResponseEntity<?> postBook(@Valid @RequestBody CreateBookInput input) {
        Either<OperationError, CreateBookResult> process = createBook.process(input);
        return mapToResponseEntity(process, HttpStatus.CREATED);
    }

    @DeleteMapping(APIRoutes.DELETE_BOOK)
    public ResponseEntity<?> deleteBook(@PathVariable("bookId") String bookId) {

        DeleteBookInput input = DeleteBookInput
                .builder()
                .bookId(bookId)
                .build();
        Either<OperationError, DeleteBookResult> process = deleteBook.process(input);
        return mapToResponseEntity(process, HttpStatus.CREATED);

    }

}
