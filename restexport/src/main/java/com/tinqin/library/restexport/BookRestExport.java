package com.tinqin.library.restexport;


import com.tinqin.library.book.api.APIRoutes;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;


public interface BookRestExport {

    @GetMapping(path = APIRoutes.API_BOOK_UUIDS, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getBookIDList(
            @RequestParam(name = "title", required = false, defaultValue = "") String title,
            @Valid @RequestParam(name = "authorId", required = false, defaultValue = "") String authorId,
            @RequestParam(name = "createDateMin", required = false) LocalDate createDateMin,
            @RequestParam(name = "createDateMax", required = false) LocalDate createDateMax,
            @RequestParam(name = "pageMin", required = false) Integer pageMin,
            @RequestParam(name = "pageMax", required = false) Integer pageMax)
            ;

}


