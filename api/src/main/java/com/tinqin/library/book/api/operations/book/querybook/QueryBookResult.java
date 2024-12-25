package com.tinqin.library.book.api.operations.book.querybook;


import com.tinqin.library.book.api.base.ProcessorResult;
import com.tinqin.library.book.api.models.book.BookModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class QueryBookResult implements ProcessorResult {

    private  List<BookModel> bookModelList;

}
