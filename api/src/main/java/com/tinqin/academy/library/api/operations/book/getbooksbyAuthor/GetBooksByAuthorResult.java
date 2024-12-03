package com.tinqin.academy.library.api.operations.book.getbooksbyAuthor;


import com.tinqin.academy.library.api.base.ProcessorResult;
import com.tinqin.academy.library.api.models.book.BookModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetBooksByAuthorResult implements ProcessorResult {

    private  List<BookModel> bookModels;

}
