package com.tinqin.library.book.core.convertes;

import com.tinqin.library.book.api.operations.book.getbook.GetBookResult;
import com.tinqin.library.book.persistence.models.Book;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookEntityToGetBookResult implements Converter<Book, GetBookResult> {
    @Override
    public GetBookResult convert(Book book) {
        return GetBookResult
                .builder()
                .title(book.getTitle())
                .pages(book.getPages() )
                .price(book.getPrice())
                .pricePerRental(book.getPricePerRental())
                .stock(book.getStock())
                .createdOn(book.getCreatedOn())
                .updatedOn(book.getUpdatedOn())
                .isDeleted(book.getIsDeleted())
                .authors(
                        book.getAuthors()
                                .stream()
                                .map(author -> new GetBookResult.GetBookAuthor(
                                        author.getId().toString(),
                                        author.getFirstName(),
                                        author.getLastName()))
                                .toList()
                )

                .build();
    }
}
