package com.tinqin.academy.library.api.model.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class BookModel {
    private String author;
    private String title;
    private String pages;
}
