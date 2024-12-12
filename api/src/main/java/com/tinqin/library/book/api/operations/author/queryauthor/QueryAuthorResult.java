package com.tinqin.library.book.api.operations.author.queryauthor;


import com.tinqin.library.book.api.base.ProcessorResult;
import com.tinqin.library.book.api.models.author.AuthorModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class QueryAuthorResult implements ProcessorResult {

    private  List<AuthorModel> authorModelList ;

}
