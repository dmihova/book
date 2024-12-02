package com.tinqin.academy.library.persistence.filereaderfactory.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class BookSeederModel {
    private final String title;
    private final Integer pages;
    private final Double price;
    private final String authorFirstName;
    private final String authorLastName;
    private final String secondAuthorFirstName;
    private final String secondAuthorLastName;

}
