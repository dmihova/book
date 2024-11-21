package com.tinqin.academy.library.api.operations.querybook;


import com.tinqin.academy.library.api.base.ProcessorResult;
import com.tinqin.academy.library.api.model.book.BookModel;
import com.tinqin.academy.library.api.operations.getbook.GetBookOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class QueryBookResult implements ProcessorResult {

    private  List<BookModel> books ;

}
