package com.tinqin.academy.library.api.model.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
public class BookModel {
    private UUID id;
    private String author;
    private String title;
    private String pages;
}
