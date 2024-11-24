package com.tinqin.academy.library.api.operations.createbook;


import com.tinqin.academy.library.api.base.ProcessorOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateBookOutput implements ProcessorOutput {
    private final UUID id;
}
