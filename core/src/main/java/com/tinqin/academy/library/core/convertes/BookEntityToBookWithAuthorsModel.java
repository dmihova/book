package com.tinqin.academy.library.core.convertes;


import com.tinqin.academy.library.api.models.book.BookWithAuthorsModel;
import com.tinqin.academy.library.persistence.models.Book;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BookEntityToBookWithAuthorsModel implements Converter<Book, BookWithAuthorsModel> {

    @Override
    public BookWithAuthorsModel convert(Book source) {
         return BookWithAuthorsModel
                .builder()
                .title(source.getTitle())
                .authors(source.getAuthors()
                         .stream()
                         .map(author ->author.getFullName())
                         .collect(Collectors.joining(", "))          )
                .pages(source.getPages())
                .id(source.getId())
                .build();
    }
}
