package com.tinqin.academy.library.core.processors.book;

import com.tinqin.academy.library.api.operations.getbook.GetBook;
import com.tinqin.academy.library.api.operations.getbook.GetBookInput;
import com.tinqin.academy.library.api.operations.getbook.GetBookOutput;
import com.tinqin.academy.library.persistence.models.Book;
import com.tinqin.academy.library.persistence.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import java.util.UUID;



@Service
@RequiredArgsConstructor
public class GetBookProcessor implements GetBook {

    private final BookRepository bookRepository;

    @Override
    public GetBookOutput process(GetBookInput input) {
        Book book = fetchBook(input);
        return convertGetBookInputToGetBookOutput(book);
    }

    private Book fetchBook(GetBookInput input) {
        try {
            UUID id = UUID.fromString(input.getBookId());
        } catch (Exception ex) {
            throw new  RuntimeException("Book uuid format wrong");
        }
        return  bookRepository.findById(UUID.fromString(input.getBookId()))
                .orElseThrow(() ->  new RuntimeException("Book not found"));

    }

    private GetBookOutput convertGetBookInputToGetBookOutput(Book book) {
        return GetBookOutput.builder()
                .author(String.valueOf(book.getAuthor()))
                .title(book.getTitle())
                .pages(book.getPages())
                .build();
    }
}
