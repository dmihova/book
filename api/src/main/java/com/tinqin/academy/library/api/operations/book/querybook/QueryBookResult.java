package com.tinqin.academy.library.api.operations.book.querybook;


import com.tinqin.academy.library.api.base.ProcessorResult;
import com.tinqin.academy.library.api.models.book.BookWithAuthorsModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class QueryBookResult implements ProcessorResult {

    private  List<BookWithAuthorsModel> bookModelList;

}
