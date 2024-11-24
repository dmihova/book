package com.tinqin.academy.library.core.convertes;


import com.tinqin.academy.library.api.operations.createauthor.CreateAuthorInput;
import com.tinqin.academy.library.persistence.models.Author;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateAuthorInputToAuthor implements Converter<CreateAuthorInput, Author> {

    @Override
    public Author convert(CreateAuthorInput source) {
        return Author
                .builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .build();
    }
}
