package com.tinqin.library.restexport;


import com.tinqin.library.book.api.APIRoutes;
import com.tinqin.library.book.api.operations.book.getbooksidlist.GetBooksIdListInput;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.SpringQueryMap;
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
            @RequestParam(name = "sizeMin", required = false) Integer sizeMin,
            @RequestParam(name = "sizeMax", required = false) Integer sizeMax)
            ;

    @GetMapping(path = APIRoutes.API_BOOK_UUIDS_V2, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
     ResponseEntity<?> getBookIDListV2(@SpringQueryMap GetBooksIdListInput param);


}


