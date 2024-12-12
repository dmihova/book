package com.tinqin.library.book.api.operations.author.queryauthor;


import com.tinqin.library.book.api.base.ProcessorInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class QueryAuthorInput implements ProcessorInput {

    private String firstName;
    private String lastName;

}
