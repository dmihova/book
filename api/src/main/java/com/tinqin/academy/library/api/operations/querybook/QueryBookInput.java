package com.tinqin.academy.library.api.operations.querybook;


import com.tinqin.academy.library.api.base.ProcessorInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class QueryBookInput implements ProcessorInput {

    private String title;

    private String authorId;
    private String authorFirstName;
    private String authorLastName;


}
