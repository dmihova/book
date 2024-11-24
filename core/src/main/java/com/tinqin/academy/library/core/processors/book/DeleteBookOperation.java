package com.tinqin.academy.library.core.processors.book;


import com.tinqin.academy.library.api.operations.deletebook.DeleteBook;
import com.tinqin.academy.library.api.operations.deletebook.DeleteBookInput;
import com.tinqin.academy.library.api.operations.deletebook.DeleteBookOutput;
import com.tinqin.academy.library.persistence.models.Book;
import com.tinqin.academy.library.persistence.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteBookOperation implements DeleteBook {
    private final BookRepository bookRepository;
    @Override
    public DeleteBookOutput process(DeleteBookInput input) {
        Book book =  bookRepository
                .findById(UUID.fromString(input.getBookId()))
                .orElseThrow(() ->  new ObjectNotFoundException(input,input.getBookId()));

        book.setIsDeleted(true);
        bookRepository.save(book);

        return DeleteBookOutput
                .builder()
                .id(String.valueOf(book.getId()))
                .build();


    }
}
