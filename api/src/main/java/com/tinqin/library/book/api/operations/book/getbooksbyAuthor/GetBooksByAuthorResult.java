package com.tinqin.library.book.api.operations.book.getbooksbyAuthor;


import com.tinqin.library.book.api.base.ProcessorResult;
import com.tinqin.library.book.api.models.book.BookModel;
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
