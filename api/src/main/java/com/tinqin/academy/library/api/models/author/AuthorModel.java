package com.tinqin.academy.library.api.models.author;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
public class AuthorModel {
    private UUID id;
    private String firstName;
    private String lastName;

}
