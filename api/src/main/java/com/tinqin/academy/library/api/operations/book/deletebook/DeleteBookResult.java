package com.tinqin.academy.library.api.operations.book.deletebook;

import com.tinqin.academy.library.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DeleteBookResult implements ProcessorResult {
    private String id;
}
