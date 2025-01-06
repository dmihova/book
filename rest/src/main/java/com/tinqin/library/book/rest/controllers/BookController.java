package com.tinqin.library.book.rest.controllers;


import com.tinqin.library.book.api.APIRoutes;
import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.book.deletebook.DeleteBook;
import com.tinqin.library.book.api.operations.book.deletebook.DeleteBookInput;
import com.tinqin.library.book.api.operations.book.deletebook.DeleteBookResult;
import com.tinqin.library.book.api.operations.book.getbook.GetBook;
import com.tinqin.library.book.api.operations.book.getbook.GetBookInput;
import com.tinqin.library.book.api.operations.book.getbook.GetBookResult;
import com.tinqin.library.book.api.operations.book.createbook.CreateBook;
import com.tinqin.library.book.api.operations.book.createbook.CreateBookInput;
import com.tinqin.library.book.api.operations.book.createbook.CreateBookResult;
import com.tinqin.library.book.api.operations.book.getbooksbyAuthor.GetBooksByAuthor;
import com.tinqin.library.book.api.operations.book.getbooksbyAuthor.GetBooksByAuthorInput;
import com.tinqin.library.book.api.operations.book.getbooksbyAuthor.GetBooksByAuthorResult;
import com.tinqin.library.book.api.operations.book.getbooksidlist.GetBooksIdList;
import com.tinqin.library.book.api.operations.book.getbooksidlist.GetBooksIdListInput;
import com.tinqin.library.book.api.operations.book.getbooksidlist.GetBooksIdListResult;
import com.tinqin.library.book.api.operations.book.partialedit.PartialEditBook;
import com.tinqin.library.book.api.operations.book.partialedit.PartialEditBookInput;
import com.tinqin.library.book.api.operations.book.partialedit.PartialEditBookResult;
import com.tinqin.library.book.api.operations.book.querybook.QueryBook;
import com.tinqin.library.book.api.operations.book.querybook.QueryBookInput;
import com.tinqin.library.book.api.operations.book.querybook.QueryBookResult;
import com.tinqin.library.book.rest.controllers.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
@RestController

public class BookController extends BaseController {
    private static final String postDescription =
            """
                    Get book  by title (wildcard)
                    author:authorId or author first name (wildcard) or author last name (wildcard) or
                    price range - priceMin&priceMax
                    price per rental age -pricePerRentalMin& pricePerRentalMax
                    stock range stockMin& stockMax
                    isDeleted flag
                    """;

    private final GetBook getBook;
    private final QueryBook queryBook;
    private final GetBooksByAuthor getBooksByAuthor;
    private final GetBooksIdList getBooksIdList;

    private final CreateBook createBook;
    private final PartialEditBook partialEditBook;
    private final DeleteBook deleteBook;


    @Operation(summary = "Get book UUID",
            description = "Get book details by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not found")})

    @GetMapping(APIRoutes.API_BOOKS_ID)
    public ResponseEntity<?> getBook(@PathVariable("bookId") String bookId) {
        GetBookInput bookInput = GetBookInput
                .builder()
                .bookId(bookId)
                .build();

        Either<OperationError, GetBookResult> result = getBook.process(bookInput);
        return mapToResponseEntity(result, HttpStatus.OK);
    }


    @Operation(summary = "Get books by multiple criteria",
            description = postDescription
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not found")})
    @GetMapping(APIRoutes.API_BOOK)
    public ResponseEntity<?> getBooks(
            @RequestParam(name = "title", required = false, defaultValue = "") String title,
            @Valid @RequestParam(name = "authorId", required = false) String authorId,
            @RequestParam(name = "authorFirstName", required = false, defaultValue = "") String authorFirstName,
            @RequestParam(name = "authorLastName", required = false, defaultValue = "") String authorLastName,
            @Valid @RequestParam(name = "priceMin", required = false) BigDecimal priceMin,
            @Valid @RequestParam(name = "priceMax", required = false) BigDecimal priceMax,
            @Valid @RequestParam(name = "pricePerRentalMin", required = false) BigDecimal pricePerRentalMin,
            @Valid @RequestParam(name = "pricePerRentalMax", required = false) BigDecimal pricePerRentalMax,
            @Valid @RequestParam(name = "stockMin", required = false) Integer stockMin,
            @Valid @RequestParam(name = "stockMax", required = false) Integer stockMax,
            @RequestParam(name = "isDeleted", required = false) Boolean isDeleted,
            @SortDefault(sort = "title", direction = Sort.Direction.ASC)
            @PageableDefault(size = 20
            ) Pageable pageable

    ) {

        QueryBookInput input = QueryBookInput
                .builder()
                .title(title)
                .authorId(authorId)
                .authorFirstName(authorFirstName)
                .authorLastName(authorLastName)
                .priceMin(priceMin)
                .priceMax(priceMax)
                .pricePerRentalMin(pricePerRentalMin)
                .pricePerRentalMax(pricePerRentalMax)
                .stockMin(stockMin)
                .stockMax(stockMax)
                .isDeleted(isDeleted)
                .pageable(pageable)
                .build();
        Either<OperationError, QueryBookResult> result = queryBook.process(input);
        return mapToResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(APIRoutes.API_BOOK_BY_AUTHOR)
    public ResponseEntity<?> getBooksByAuthor(@PathVariable(name = "authorId") String authorId,
                                              @SortDefault(sort = "title", direction = Sort.Direction.ASC)
                                              @PageableDefault(size = 20  ///page = 0 is default value
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

    @DeleteMapping(APIRoutes.API_BOOKS_ID)
    public ResponseEntity<?> deleteBook(@PathVariable("bookId") String bookId) {

        DeleteBookInput input = DeleteBookInput
                .builder()
                .bookId(bookId)
                .build();
        Either<OperationError, DeleteBookResult> process = deleteBook.process(input);
        return mapToResponseEntity(process, HttpStatus.CREATED);

    }

    @PatchMapping(APIRoutes.API_BOOKS_ID)
    public ResponseEntity<?> partialEditBook(@PathVariable("bookId") String bookId,
                                             @Valid @RequestBody PartialEditBookInput request) {

        PartialEditBookInput input = request
                .toBuilder()
                .bookId(bookId)
                .build();

        Either<OperationError, PartialEditBookResult> process = partialEditBook.process(input);
        return mapToResponseEntity(process, HttpStatus.OK);
    }


    @GetMapping(APIRoutes.API_BOOK_UUIDS)
    public ResponseEntity<?> getBookIDList(
            @RequestParam(name = "title", required = false, defaultValue = "") String title,
            @Valid @RequestParam(name = "authorId", required = false ) String authorId,
            @RequestParam(name = "createDateMin", required = false ) LocalDate createDateMin,
            @RequestParam(name = "createDateMax", required = false ) LocalDate createDateMax,
            @RequestParam(name = "pageMin", required = false ) Integer pageMin,
            @RequestParam(name = "pageMax", required = false ) Integer pageMax
    ) {

        GetBooksIdListInput  input =  GetBooksIdListInput
                .builder()
                .title( title)
                .authorId( authorId)
                .createDateMin(createDateMin)
                .createDateMax(createDateMax)
                .pageMin(pageMin)
                .pageMax(pageMax)
                .build();


        Either<OperationError, GetBooksIdListResult> result = getBooksIdList.process(input);
        return mapToResponseEntity(result, HttpStatus.OK);
    }





}
