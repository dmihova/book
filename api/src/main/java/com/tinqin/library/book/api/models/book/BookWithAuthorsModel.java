package com.tinqin.library.book.api.models.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
public class BookWithAuthorsModel {
    private UUID id;
    private String authors;
    private String title;
    private String pages;




}
