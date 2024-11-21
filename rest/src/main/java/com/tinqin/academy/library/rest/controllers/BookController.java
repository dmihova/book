package com.tinqin.academy.library.rest.controllers;

import com.tinqin.academy.library.api.APIRoutes;
import com.tinqin.academy.library.api.operations.getbook.GetBook;
import com.tinqin.academy.library.api.operations.getbook.GetBookInput;
import com.tinqin.academy.library.api.operations.getbook.GetBookOutput;
import com.tinqin.academy.library.api.operations.postbook.PostBook;
import com.tinqin.academy.library.api.operations.postbook.PostBookInput;
import com.tinqin.academy.library.api.operations.postbook.PostBookResult;
import com.tinqin.academy.library.api.operations.querybook.QueryBook;
import com.tinqin.academy.library.api.operations.querybook.QueryBookInput;
import com.tinqin.academy.library.api.operations.querybook.QueryBookResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController

public class BookController {
/// to do @ControllerAdvice class to handle
    private final GetBook getBook;
    private final QueryBook queryBook;
    private final PostBook postBook;


    @GetMapping(APIRoutes.GET_BOOK)
    public ResponseEntity<?> getBook(@PathVariable("bookId") String bookId) {
        GetBookInput input = GetBookInput
                .builder()
                .bookId(bookId)
                .build();
        try {
            GetBookOutput result = getBook.process(input);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (RuntimeException exc) { /// some custom  exceptions?
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(APIRoutes.API_BOOK)
    public  ResponseEntity<?> getBooks(
                @RequestParam(value ="title", required = false,defaultValue = "") String title,
                @RequestParam(value ="author", required = false ,defaultValue = "") String author   ) {

        QueryBookInput input = QueryBookInput
                .builder()
                .title(title)
                .author(author)
                .build();
        QueryBookResult  result = queryBook.process(input);
        return new ResponseEntity<>(result, HttpStatus.OK);


    }
    @PostMapping(APIRoutes.API_BOOK)
    public  ResponseEntity<?>  postBooks( @RequestBody PostBookInput input ) {
        PostBookResult result = postBook.process(input);
        return new ResponseEntity<>(result, HttpStatus.CREATED);

    }

}
