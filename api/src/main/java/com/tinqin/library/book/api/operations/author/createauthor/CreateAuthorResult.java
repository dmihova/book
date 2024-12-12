package com.tinqin.library.book.api.operations.author.createauthor;


import com.tinqin.library.book.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateAuthorResult implements ProcessorResult {
    private final UUID id;
}
