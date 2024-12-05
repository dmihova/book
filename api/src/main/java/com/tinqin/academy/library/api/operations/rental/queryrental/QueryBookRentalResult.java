package com.tinqin.academy.library.api.operations.rental.queryrental;


import com.tinqin.academy.library.api.base.ProcessorResult;
import com.tinqin.academy.library.api.models.rental.BookRentalModel;
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
