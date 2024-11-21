package com.tinqin.academy.library.api.operations.getbook;

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
public class GetBookOutput implements ProcessorResult {
    private String author;
    private String title;
    private String pages;
}
