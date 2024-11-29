package com.tinqin.academy.library.core.convertes;


import com.tinqin.academy.library.api.models.book.BookModel;
import com.tinqin.academy.library.persistence.models.Book;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookEntityToBookModel implements Converter<Book, BookModel> {

    @Override
    public BookModel convert(Book source) {
        return BookModel
                .builder()
                .title(source.getTitle())
                .author(source.getAuthor().getFullName())
                .pages(source.getPages())
                .id(source.getId())
                .build();
    }
}