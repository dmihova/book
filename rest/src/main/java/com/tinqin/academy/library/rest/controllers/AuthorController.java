package com.tinqin.academy.library.rest.controllers;

import com.tinqin.academy.library.api.APIRoutes;
import com.tinqin.academy.library.api.operations.createauthor.CreateAuthor;
import com.tinqin.academy.library.api.operations.createauthor.CreateAuthorInput;
import com.tinqin.academy.library.api.operations.createauthor.CreateAuthorOutput;
import com.tinqin.academy.library.api.operations.getauthor.GetAuthor;
import com.tinqin.academy.library.api.operations.getauthor.GetAuthorInput;
import com.tinqin.academy.library.api.operations.getauthor.GetAuthorOutput;
import com.tinqin.academy.library.api.operations.queryauthor.QueryAuthor;
import com.tinqin.academy.library.api.operations.queryauthor.QueryAuthorInput;
import com.tinqin.academy.library.api.operations.queryauthor.QueryAuthorOutput;
import com.tinqin.academy.library.api.operations.querybook.QueryBookInput;
import com.tinqin.academy.library.api.operations.querybook.QueryBookOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController

public class AuthorController {

    private final GetAuthor  getAuthor;
    private final CreateAuthor createAuthor;
    private final QueryAuthor queryAuthor;


    @GetMapping(APIRoutes.GET_AUTHOR)
    public ResponseEntity<?> getAuthor(@PathVariable("authorId") String authorId) {
        GetAuthorInput getAuthorInput = GetAuthorInput
                .builder()
                .authorId(authorId)
                .build();
        GetAuthorOutput getAuthorOutput = getAuthor.process(getAuthorInput);
        return new ResponseEntity<>(getAuthorOutput, HttpStatus.OK);
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
        QueryAuthorOutput result = queryAuthor.process(input);
        return new ResponseEntity<>(result, HttpStatus.OK);


    }

    @PostMapping(APIRoutes.API_AUTHOR)
    public ResponseEntity<?> postBooks(@RequestBody CreateAuthorInput input) {

        CreateAuthorOutput process = createAuthor.process(input);
        return new ResponseEntity<>(process, HttpStatus.CREATED);

    }

}
