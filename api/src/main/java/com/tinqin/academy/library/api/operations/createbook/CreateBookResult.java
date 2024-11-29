package com.tinqin.academy.library.api.operations.createbook;


import com.tinqin.academy.library.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateBookResult implements ProcessorResult {
    private final UUID id;
}
