package com.tinqin.academy.library.api.operations.deletebook;

import com.tinqin.academy.library.api.base.ProcessorOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DeleteBookOutput implements ProcessorOutput {
    private String id;
}
