package com.tinqin.academy.library.api.models.book;

import com.tinqin.academy.library.api.models.author.AuthorModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
public class BookModel {
    private UUID id;
    private String authors;
    private String title;
    private String pages;



}
