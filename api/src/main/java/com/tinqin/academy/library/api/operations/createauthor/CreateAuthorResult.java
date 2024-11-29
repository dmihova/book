package com.tinqin.academy.library.api.operations.createauthor;


import com.tinqin.academy.library.api.base.ProcessorResult;
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
