package com.tinqin.academy.library.persistence.filereader;

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
}
