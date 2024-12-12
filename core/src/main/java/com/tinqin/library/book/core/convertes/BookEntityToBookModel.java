package com.tinqin.library.book.core.convertes;

import com.tinqin.library.book.api.models.book.BookModel;
import com.tinqin.library.book.persistence.models.Book;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookEntityToBookModel implements Converter<Book, BookModel> {
    @Override
    public BookModel convert(Book source) {
        return BookModel
                .builder()
                .id(source.getId())
                .title(source.getTitle())
                .pages(source.getPages())
                .createdAt(source.getCreatedAt())
                .price(source.getPrice())
                .stock(source.getStock())
                .build();
    }
}
