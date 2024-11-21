package com.tinqin.academy.library.api.operations.postbook;


import com.tinqin.academy.library.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class PostBookResult implements ProcessorResult {
    private final UUID id;
}
