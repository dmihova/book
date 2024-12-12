package com.tinqin.library.book.core.convertes;


import com.tinqin.library.book.api.models.author.AuthorModel;
import com.tinqin.library.book.persistence.models.Author;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuthorEntityToAuthorModel implements Converter<Author, AuthorModel> {
    @Override
    public AuthorModel convert(Author authorEntity) {
        return AuthorModel
                .builder()
                .id(authorEntity.getId())
                .firstName(authorEntity.getFirstName())
                .lastName(authorEntity.getLastName())
                .build();
    }
}
