package com.tinqin.library.book.api.operations.rental.queryrental;


import com.tinqin.library.book.api.base.ProcessorResult;
import com.tinqin.library.book.api.models.rental.BookRentalModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class QueryBookRentalResult implements ProcessorResult {

    private  List<BookRentalModel> bookRentalModels;

}
