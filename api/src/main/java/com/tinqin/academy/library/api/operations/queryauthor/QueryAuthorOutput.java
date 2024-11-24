package com.tinqin.academy.library.api.operations.queryauthor;


import com.tinqin.academy.library.api.base.ProcessorOutput;
import com.tinqin.academy.library.api.models.author.AuthorModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class QueryAuthorOutput implements ProcessorOutput {

    private  List<AuthorModel> authorModelList ;

}
