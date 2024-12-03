package com.tinqin.academy.library.api.operations.author.getauthor;

import com.tinqin.academy.library.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetAuthorResult implements ProcessorResult {
    private UUID authorId;
    private String firstName;
    private String lastName;

}

