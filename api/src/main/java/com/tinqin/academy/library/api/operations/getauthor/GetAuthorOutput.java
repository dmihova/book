package com.tinqin.academy.library.api.operations.getauthor;

import com.tinqin.academy.library.api.base.ProcessorOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetAuthorOutput implements ProcessorOutput {
    private UUID authorId;
    private String firstName;
    private String lastName;

}

