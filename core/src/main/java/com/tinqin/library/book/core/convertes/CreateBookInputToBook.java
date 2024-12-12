package com.tinqin.library.book.core.convertes;


import com.tinqin.library.book.api.operations.book.createbook.CreateBookInput;
import com.tinqin.library.book.persistence.models.Book;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateBookInputToBook  implements Converter<CreateBookInput, Book > {

    @Override
    public Book convert(CreateBookInput source) {
        return Book
                .builder()
                .title(source.getTitle())
                   .pages(source.getPages())
                .price(source.getPrice())
                .pricePerRental(source.getPrice())
                .stock(0)
                .isDeleted(false)
                .build();
    }
}
