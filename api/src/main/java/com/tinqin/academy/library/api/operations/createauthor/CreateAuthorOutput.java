package com.tinqin.academy.library.api.operations.createauthor;


import com.tinqin.academy.library.api.base.ProcessorOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateAuthorOutput implements ProcessorOutput {
    private final UUID id;
}
