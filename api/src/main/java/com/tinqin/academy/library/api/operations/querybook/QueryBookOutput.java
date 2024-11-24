package com.tinqin.academy.library.api.operations.querybook;


import com.tinqin.academy.library.api.base.ProcessorOutput;
import com.tinqin.academy.library.api.models.book.BookModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class QueryBookOutput implements ProcessorOutput {

    private  List<BookModel> bookModelList;

}
