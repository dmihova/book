package com.tinqin.library.book.api.operations.book.partialedit;


import com.tinqin.library.book.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class PartialEditBookResult implements ProcessorResult {
    private final UUID bookId;
}
