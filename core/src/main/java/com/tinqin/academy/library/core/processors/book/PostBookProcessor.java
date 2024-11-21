package com.tinqin.academy.library.core.processors.book;

import com.tinqin.academy.library.api.operations.postbook.PostBook;
import com.tinqin.academy.library.api.operations.postbook.PostBookInput;
import com.tinqin.academy.library.api.operations.postbook.PostBookResult;
import com.tinqin.academy.library.persistence.models.Book;
import com.tinqin.academy.library.persistence.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostBookProcessor implements PostBook {
    private final BookRepository bookRepository;

    @Override
    public PostBookResult process(PostBookInput input) {
        Book newBook = Book
                .builder()
                .author(input.getAuthor())
                .title(input.getTitle())
                .pages(input.getPages())
                .price(input.getPrice())
                .pricePerRental(BigDecimal.ZERO)
                .stock(0)
                .createdAt(LocalDateTime.now())

                .build();
         bookRepository.save(newBook);
        return PostBookResult.builder().id(newBook.getId()).build();

    }
}
